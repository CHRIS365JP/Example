package example.jodconverter;

import java.io.File;

import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

public class JodLocalExample {

    public static void main(String... args) throws OfficeException {
        System.out.println("1:" + System.getenv("JAVA_HOME"));
        System.out.println("2:" + System.getProperty("office.home"));
        System.out.println("3:" + System.getProperty("JAVA_HOME"));
        System.out.println("4:" + System.getProperties());
        System.out.println("officeManager start");
        
        System.setProperty("office.home", "C:\\Program Files\\LibreOffice 5");
        
        OfficeManager officeManager = LocalOfficeManager.make();
        DocumentConverter converter = LocalConverter.make(officeManager);
//      OfficeManager officeManager = LocalOfficeManager.builder().build();
//      DocumentConverter converter = LocalConverter.builder().officeManager(officeManager).build();

        officeManager.start();
        try {
            System.out.println("convert start");
            String fileName = "C:\\Users\\zzhang\\Desktop\\Temp\\1";
            String fileName2 = "C:\\Users\\zzhang\\Desktop\\Temp\\1";
//            File excelFile = new File(fileName + ".xlsx");
            File excelFile = new File(fileName2 + ".pptx");
            File pdfFile = new File(fileName + ".pdf");
            converter.convert(excelFile).to(pdfFile).execute();

            System.out.println("convert end");
        } finally {
            officeManager.stop();
        }
    }
}
