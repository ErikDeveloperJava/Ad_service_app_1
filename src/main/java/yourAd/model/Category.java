package yourAd.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private Integer id;

    private String name;

    private Category parent;

    private List<Category> childrenList;
}
