package yourAd.form;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdForm {

    private String title;

    private String description;

    private String price;

    private int categoryId;
}
