package uz.raximov.component.io.writer;

import uz.raximov.Main;
import uz.raximov.vo.CityVO;
import uz.raximov.vo.FileDataVO;

import java.io.File;

public abstract class FileWriter {
    public void write(FileDataVO fileData) {
        try {
            if (!fileData.isValid()) fileData.setPath(fileData.getPath().replace(Main.Configs.successDir, Main.Configs.failedDir));
            File path = new File(fileData.getPath());
            if (!path.exists()) path.getParentFile().mkdirs();
            java.io.FileWriter writer = new java.io.FileWriter(path);
            writer.write(data(fileData.getCityVO()));
            writer.flush();
            writer.close();
            System.out.println("File successfully wrote: " + fileData.getName());
        } catch (Exception e) {
            System.err.println("File write error: " + fileData.getName());
        }
    }

    public String data(CityVO cityVO) {
        return cityVO.getArea();
    }
}