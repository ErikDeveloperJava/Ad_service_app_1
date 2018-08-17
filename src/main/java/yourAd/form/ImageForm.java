package yourAd.form;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageForm {

    private byte[] bytes;

    private String contentType;

    private String name;
}
