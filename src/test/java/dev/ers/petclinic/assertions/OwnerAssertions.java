package dev.ers.petclinic.assertions;

import dev.ers.petclinic.model.OwnerRequest;
import dev.ers.petclinic.model.OwnerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/** Повторно используемые проверки данных owner. */
public final class OwnerAssertions {
    /** Запрещает создание экземпляров класса проверок. */
    private OwnerAssertions() {
    }

    /** Сверяет id обычной проверкой, затем собирает все расхождения полей owner. */
    public static void assertOwner(OwnerResponse actual, int expectedId, OwnerRequest expected) {
        assertThat(actual)
                .isNotNull();
        assertThat(actual.id())
                .isEqualTo(expectedId);

        assertSoftly(softly -> {
            softly.assertThat(actual.firstName())
                    .isEqualTo(expected.firstName());
            softly.assertThat(actual.lastName())
                    .isEqualTo(expected.lastName());
            softly.assertThat(actual.address())
                    .isEqualTo(expected.address());
            softly.assertThat(actual.city())
                    .isEqualTo(expected.city());
            softly.assertThat(actual.telephone())
                    .isEqualTo(expected.telephone());
        });
    }
}
