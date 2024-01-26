package andreamarchica.U5W3L5.payloads.bookings;

import jakarta.validation.constraints.NotNull;

public class NewBookingDTO {
        @NotNull(message = "Specifica l'evento a cui vuoi partecipare")
        private String title;

        public NewBookingDTO() {
        }

        public NewBookingDTO(String title) {
                this.title = title;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }
}
