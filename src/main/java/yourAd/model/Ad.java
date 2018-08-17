package yourAd.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ad {

    private int id;

    private String title;

    private String description;

    private double price;

    private Date createdDate;

    private String imgUrl;

    private Category category;

    private User user;
}
