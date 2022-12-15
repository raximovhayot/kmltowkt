package uz.raximov.component.io.reader;

import uz.raximov.Main;
import uz.raximov.vo.CityVO;
import uz.raximov.vo.FileDataVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SimpleFileReader implements FileReader {
    private final Long marketplaceId;
    private final Boolean custom;
    private final Long countryId;

    public SimpleFileReader(Long marketplaceId, Boolean custom, Long countryId) {
        this.marketplaceId = marketplaceId;
        this.custom = custom;
        this.countryId = countryId;
    }

    @Override
    public List<FileDataVO> read(File dir) {
        List<FileDataVO> fileData = new ArrayList<>();
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.isDirectory()) {
                fileData.addAll(read(f));
                continue;
            }
            String fileName = f.getName();
            Long id = null;
            if (f.getName().contains(":")){
                String[] strings = f.getName().split(":");
                fileName = strings[0];
                id = Long.valueOf(strings[1]);
            }
            FileDataVO file = FileDataVO.builder()
                    .name(fileName)
                    .path(f.getPath())
                    .cityVO( CityVO.builder()
                            .id(id)
                            .area(readContent(f))
                            .marketplace_id(marketplaceId)
                            .custom(custom)
                            .country_id(countryId)
                            .name(fileName.replace(Main.Configs.TARGET_FILE_EXTENSION, ""))
                            .code(fileName.toLowerCase().replace(Main.Configs.TARGET_FILE_EXTENSION, "").replace(" ", "_"))
                            .import_code(fileName.toLowerCase().replace(Main.Configs.TARGET_FILE_EXTENSION, "").replace(" ", "_"))
                            .parent_name(f.getParentFile().getName())
                            .build() )
                    .build();

            fileData.add(file);
        }
        return fileData;
    }

    @Override
    public String readContent(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            System.err.println("File read error: File name: " + file.getName());
        }
        return stringBuilder.toString();
    }
}
