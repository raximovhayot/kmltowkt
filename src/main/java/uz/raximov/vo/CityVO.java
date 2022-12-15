package uz.raximov.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CityVO {
    private Long id;
    private String area;
    private String name;
    private String code;
    private String import_code;
    private Long country_id;
    private String parent_name;
    private Long marketplace_id;
    private Boolean custom;
}
