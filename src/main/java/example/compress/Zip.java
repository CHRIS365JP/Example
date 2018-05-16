package example.compress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class Zip {
    
    public static void main(String[] args) {
//        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\1.docx";
////        File file = new File(filePath);
////        InputStream is = new BufferedInputStream(new FileInputStream(filePath));
////        ZipFile zipOutStream = new ZipFile(is);
////        
////        InputStream is = new BufferedInputStream(new FileInputStream(filePath)); //really a binary OLE2 Word file
//        
////        ZipArchiveEntry entry = new ZipArchiveEntry(filePath);
////        entry.setSize(size);
////        zipOutput.putArchiveEntry(entry);
////        zipOutput.write(contentOfEntry);
////        zipOutput.closeArchiveEntry();
//        
//        ZipArchiveEntry entry = ZipFile.getEntry(filePath);
//        InputStream content = ZipFile.getInputStream(entry);
//        try {
//            READ UNTIL content IS EXHAUSTED
//        } finally {
//            content.close();
//        }
        File file = new File("C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\新規 Microsoft Word 文書.docx");
        File baseDir = new File("C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files");
//        String baseDir = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\";
        try {
            Zipper.createZipFile(file, baseDir, "UTF-8");
        } catch (ArchiveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
