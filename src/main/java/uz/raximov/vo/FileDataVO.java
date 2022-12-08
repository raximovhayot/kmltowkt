package uz.raximov.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileDataVO {
    private boolean valid;
    private String name;
    private String path;
    private CityVO cityVO;
}
