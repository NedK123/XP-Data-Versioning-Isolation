package org.example.orderservice.core.validation;

import lombok.*;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderValidationDefinitionRequest {
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Optional<String> name = Optional.empty();
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Optional<Set<OrderChecks>> checks = Optional.empty();

    public void ifContainsNameUpdate(Consumer<String> consumer) {
        name.ifPresent(consumer::accept);
    }

    public void ifContainsChecksUpdate(Consumer<Set<OrderChecks>> consumer) {
        checks.ifPresent(consumer::accept);
    }

}
