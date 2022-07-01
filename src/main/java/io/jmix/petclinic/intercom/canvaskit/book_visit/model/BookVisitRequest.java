package io.jmix.petclinic.intercom.canvaskit.book_visit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookVisitRequest {
    String ownerEmail;
    String pet;
    String visitDescription;
}
