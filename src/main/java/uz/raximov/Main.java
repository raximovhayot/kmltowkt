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
    public static final class Configs {

        private Configs() {}

        private static final String TARGET_DIR = "kml1";
        public static final String SUCCESS_DIR = "success";
        public static final String FAILED_DIR = "failed";
        public static final String TARGET_FILE_EXTENSION = ".kml";
        public static final String SUCCESS_FILE_EXTENSION = ".sql";
        public static final Long MARKETPLACE_ID = 1L;
        public static final Boolean CUSTOM = false;
        public static final Long COUNTRY_ID = 64L;
        public static final FileWriter fileWriter = new SqlWriter();
        public static final FileReader fileReader = new SimpleFileReader(Configs.MARKETPLACE_ID, Configs.CUSTOM,  Configs.COUNTRY_ID);
    }

    public static void main(String[] args) {
        final TrailForksHandler trailForksHandler = new TrailForksHandler();
        FileWriter fileWriter = Configs.fileWriter;
        FileReader fileReader = Configs.fileReader;

        List<FileDataVO> files = fileReader.read(new File(Configs.TARGET_DIR));
        files.stream()
                .peek(fd -> {
                    WKTResponse response = trailForksHandler.request(new RMSDObject(fd.getCityVO().getArea()), new Gson());
                    fd.setValid(response.isStatus());
                    fd.getCityVO().setArea(response.getRmsD());
                    fd.setPath(fd.getPath().replace(Configs.TARGET_FILE_EXTENSION, Configs.SUCCESS_FILE_EXTENSION));
                    fd.setPath(fd.getPath().replace(Configs.TARGET_DIR, Configs.SUCCESS_DIR));
                })
                .forEach(fileWriter::write);
    }
}