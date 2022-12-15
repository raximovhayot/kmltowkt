package uz.raximov.component.io.writer;

import uz.raximov.vo.CityVO;

public class SqlWriter extends FileWriter {
    @Override
    public String data(CityVO cityVO, boolean insert) {
        return insert ? insert(cityVO) : update(cityVO);
    }

    private String insert(CityVO cityVO) {
        return """
                do language plpgsql
                $$
                     declare
                         newId bigint;
                     BEGIN
                         newId := nextval('hibernate_sequence');
                         raise notice '%', newId;
                         
                         INSERT INTO emirate_areas(id, name, code, import_code, country_id, active, type, category, zindex, support2gis, level, parent_name, marketplace_id, CUSTOM)
                         values (newId, ':name', ':code', ':import_code', :country_id, true, 'DOMESTIC', 'CITY', 0, false, 'L1', ':parent_name', :marketplace_id, :CUSTOM);
                             
                         :updateQuery
                     END
                $$;
                """.replace(":name", cityVO.getName())
                .replace(":code", cityVO.getCode())
                .replace(":import_code", cityVO.getImport_code())
                .replace(":country_id", String.valueOf(cityVO.getCountry_id()))
                .replace(":parent_name", cityVO.getParent_name())
                .replace(":marketplace_id", cityVO.getMarketplace_id().toString())
                .replace(":CUSTOM", cityVO.getCustom() ? "true" : "false")
                .replace(":updateQuery", update(cityVO));
    }

    private String update(CityVO cityVO) {
        return """
                         UPDATE emirate_areas\s
                         SET areas = ST_Multi(st_setSRID(st_PolygonFromText(':polygon'), 4326))
                         WHERE id = :id;
                """
                .replace(":polygon", cityVO.getArea())
                .replace(":id", String.valueOf(cityVO.getId()) != null ? String.valueOf(cityVO.getId()) : "newId");
    }
}
