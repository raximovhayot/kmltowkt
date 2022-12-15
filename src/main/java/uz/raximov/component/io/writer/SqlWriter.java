package uz.raximov.component.io.writer;

import uz.raximov.vo.CityVO;

public class SqlWriter extends FileWriter {
    @Override
    public String data(CityVO cityVO) {
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
                            
                        UPDATE emirate_areas\s
                        SET areas = ST_Multi(ST_MakePolygon(ST_GeomFromText(':polygon')))
                        WHERE id = newId;
                    END
               $$;
               """.replace(":name", cityVO.getName())
                  .replace(":code", cityVO.getCode())
                  .replace(":import_code", cityVO.getImport_code())
                  .replace(":country_id", String.valueOf(cityVO.getCountry_id()))
                  .replace(":parent_name", cityVO.getParent_name())
                  .replace(":marketplace_id", cityVO.getMarketplace_id().toString())
                  .replace(":CUSTOM", cityVO.getCustom() ? "true" : "false")
                  .replace(":polygon", cityVO.getArea());
    }
}
