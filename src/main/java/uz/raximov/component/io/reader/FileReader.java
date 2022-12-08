package uz.raximov.component.io.reader;

import uz.raximov.vo.FileDataVO;

import java.io.File;
import java.util.List;

public interface FileReader {
    List<FileDataVO> read(File dir);
    String readContent(File file);
}
