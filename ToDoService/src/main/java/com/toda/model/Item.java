package com.toda.model;


 import com.fasterxml.jackson.annotation.JsonIgnore;
 import io.swagger.v3.oas.annotations.media.Schema;
 import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;



@Entity
@Table(name = "item")
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Validated
@Schema(description = "component about the item")
 public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
    @Schema(description = "unique id of the item")
    private int id;



    @Column(name = "title")
        private String title;

    @Column(name = "user_id")
    @Schema(description = "the user id which is the email for specific user")
    private String userId;

    @OneToOne(cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    @JsonIgnore // This prevents the itemDetails from being serialized
    @JoinColumn(name = "item_details_id")
    private Item_Details itemDetails;

    public Item(String title) {
        this.title = title;
    }
}
