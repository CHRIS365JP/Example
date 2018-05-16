package example.jacob;

import java.io.File;

public interface Office2PDFInterface {
    
    File convert2PDF(File msoFile) throws Exception;
    
    File convert2PDF(File msoFile, String fileType, String outputPath) throws Exception;
    
//    void convert2PDF(File msoFIle, String fileType, String outputPath) throws Exception;
}
