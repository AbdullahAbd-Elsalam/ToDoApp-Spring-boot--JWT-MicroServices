package com.toda.model;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import io.swagger.v3.oas.annotations.media.Schema;
 import jakarta.persistence.*;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "item_details")
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
 @Validated
@Schema(description = " the details of specific item")
public class Item_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "unique id of item details")
    private Long id;

    @Column(name = "description")
    private String description;


    @NotNull(message = "Creation date is mandatory")
    @Temporal(TemporalType.DATE)
    @Schema(description = "the time which details are created")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
     private Priority priority;

    @Column(name = "status")
    @NotNull(message = "this filed cannot be null")
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "itemDetails" )
    @JsonIgnore
    private Item item;


}
