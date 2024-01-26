package andreamarchica.U5W3L5.payloads.events;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "Il titolo è obbligatorio")
        String title,
        @NotEmpty(message = "La descrizione è obbligatoria")
        @Size(min = 5, max = 100, message = "La descrizione deve avere minimo 5 caratteri, massimo 100")
        String description,
        @NotNull(message = "La data è obbligatoria")
        LocalDate eventDate,
        @NotEmpty(message = "La location è obbligatoria")
        String location,
        @NotNull(message = "I posti disponibili sono obbligatori")
        int availablePlaces,
        @NotEmpty(message = "L'immagine dell'evento è obbligatoria")
        String eventImage
) {

}
