package uz.raximov;

import com.google.gson.Gson;
import lombok.Getter;
import uz.raximov.component.TrailForksHandler;
import uz.raximov.component.io.reader.FileReader;
import uz.raximov.component.io.reader.SimpleFileReader;
import uz.raximov.component.io.writer.FileWriter;
import uz.raximov.component.io.writer.SqlWriter;
import uz.raximov.payload.RMSDObject;
import uz.raximov.payload.WKTResponse;
import uz.raximov.vo.FileDataVO;

import java.io.File;
import java.util.List;

public class Main {

    @Getter
    public static class Configs {
        public static String targetDir = "kml";
        public static String successDir = "success";
        public static String failedDir = "failed";
        public static String targetFileExtension = ".kml";
        public static String successFileExtension = ".sql";
        public static Long marketplaceId = 1L;
        public static Boolean custom = false;
        public static Long countryId = 64L;
        public static FileWriter fileWriter = new SqlWriter();
        public static FileReader fileReader = new SimpleFileReader(Configs.marketplaceId, Configs.custom,  Configs.countryId);
    }

    public static void main(String[] args) {
        final TrailForksHandler trailForksHandler = new TrailForksHandler();
        FileWriter fileWriter = Configs.fileWriter;
        FileReader fileReader = Configs.fileReader;

        List<FileDataVO> files = fileReader.read(new File(Configs.targetDir));
        files.stream()
                .peek(fd -> {
                    WKTResponse response = trailForksHandler.request(new RMSDObject(fd.getCityVO().getArea()), new Gson());
                    fd.setValid(response.isStatus());
                    fd.getCityVO().setArea(response.getRmsD());
                    fd.setPath(fd.getPath().replace(Configs.targetFileExtension, Configs.successFileExtension));
                    fd.setPath(fd.getPath().replace(Configs.targetDir, Configs.successDir));
                })
                .forEach(fileWriter::write);
    }
}