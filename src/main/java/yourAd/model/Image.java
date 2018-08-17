package yourAd.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    private int id;

    private String url;

    private Ad ad;
}
