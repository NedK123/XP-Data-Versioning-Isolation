package org.example.orderservice.api.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderValidationController implements OrderValidationApi {

    private OrderValidationDefinitionService validationService;

    @Override
    public ResponseEntity<String> create(CreateOrderValidationDefinitionApiRequest request) {
        OrderValidationDefinition definition = validationService.create(map(request));
        return ResponseEntity.created(URI.create(definition.getId())).build();
    }

    @Override
    public ResponseEntity<OrderValidationDefinition> fetch(String definitionId, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException {
        return ResponseEntity.ok(validationService.fetch(definitionId, revisionId));
    }

    @Override
    public ResponseEntity<OrderValidationDefinition> fetch(String definitionId, String mostRecentAtDate) throws OrderValidationDefinitionNotFoundException, ParseException {
        log.info("Received fetch validation definition({}) on time request with date={}", definitionId, mostRecentAtDate);
        return ResponseEntity.ok(validationService.fetch(definitionId, from(mostRecentAtDate)));
    }

    @Override
    public ResponseEntity<Void> edit(String definitionId, EditOrderValidationDefinitionApiRequest request) throws OrderValidationDefinitionNotFoundException {
        validationService.edit(definitionId, buildEditRequest(request));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(String definitionId) throws OrderValidationDefinitionNotFoundException {
        validationService.delete(definitionId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(OrderValidationDefinitionNotFoundException.class)
    public ResponseEntity<String> handle(OrderValidationDefinitionNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    private Date from(String value) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
    }

    private static EditOrderValidationDefinitionRequest buildEditRequest(EditOrderValidationDefinitionApiRequest request) {
        EditOrderValidationDefinitionRequest.EditOrderValidationDefinitionRequestBuilder builder = EditOrderValidationDefinitionRequest.builder();
        if (Objects.nonNull(request.getName())) {
            builder.name(Optional.of(request.getName()));
        }
        if (Objects.nonNull(request.getChecks())) {
            builder.checks(Optional.of(request.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet())));
        }
        return builder.build();
    }

    private static CreateOrderValidationDefinitionRequest map(CreateOrderValidationDefinitionApiRequest request) {
        return CreateOrderValidationDefinitionRequest.builder().name(request.getName())
                .checks(request.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet())).build();
    }

}
