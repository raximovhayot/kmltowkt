package uz.raximov.component.io.writer;

import uz.raximov.vo.CityVO;

public class KmlWriter extends FileWriter{

    @Override
    public String data(CityVO cityVO) {
        return cityVO.getArea().replace("LinearRing", "LineString")
                .replace(",0", "");
    }
}
