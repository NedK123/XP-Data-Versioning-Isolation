package org.example.orderservice.core.process;

import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderProcessDefinitionRequest {
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Optional<String> name = Optional.empty();
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Optional<List<String>> validationDefinitions = Optional.empty();
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Optional<String> shippingPreparationDefinition = Optional.empty();

    public void ifContainsNameUpdate(Consumer<String> consumer) {
        name.ifPresent(consumer::accept);
    }

    public void ifContainsShippingPreparationDefinitionUpdate(Consumer<String> consumer) {
        shippingPreparationDefinition.ifPresent(consumer::accept);
    }

    public void ifContainsValidationDefinitionsUpdate(Consumer<List<String>> consumer) {
        validationDefinitions.ifPresent(consumer::accept);
    }

}
