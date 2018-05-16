package example.poi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadWord2 {
    static String read(InputStream is) throws Exception {

        System.out.println(FileMagic.valueOf(is));

        String text = "";

        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
            WordExtractor ex = new WordExtractor(is);
            text = ex.getText();
            ex.close();
        } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
            XWPFDocument doc = new XWPFDocument(is);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            text = extractor.getText();
            extractor.close();
        }

        return text;

    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\1.docx";
        InputStream is = new BufferedInputStream(new FileInputStream(filePath)); //really a binary OLE2 Word file
        System.out.println(read(is));
        is.close();

//        is = new BufferedInputStream(new FileInputStream(filePath)); //a OOXML Word file named *.doc
//        System.out.println(read(is));
//        is.close();
//
//        is = new BufferedInputStream(new FileInputStream(filePath)); //really a OOXML Word file
//        System.out.println(read(is));
//        is.close();

    }
}