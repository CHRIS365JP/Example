package example.jacob;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang3.StringUtils;

import com.jacob.com.LibraryLoader;

public class Main {

    public static void main(String[] args) {
        
        ClassLoader cl1 = ClassLoader.getSystemClassLoader();
        ClassLoader cl2 = Main.class.getClassLoader();    
        System.out.println("" + (cl1 == cl2));
        System.out.println("" + (URLClassLoader)cl1);  
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }
        System.out.println("URL END");
        
        System.out.println(System.getenv("path"));
        String libFile = "amd64".equals(System.getProperty("os.arch")) ? "jacob-1.18-x64.dll" : "jacob-1.18-x86.dll";
        String libPath = Main.class.getResource("/").getPath();
        libPath +=  libFile;
//        libPath = StringUtils.substringAfter(libPath, "/");
//        libPath = StringUtils.replaceAll(libPath, "/", "\\\\");
        
        System.out.println(libPath);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, libPath);
        System.out.println(System.getProperty(LibraryLoader.JACOB_DLL_PATH));
        
        String path1 = "C:\\Work\\SoftWare\\toPDF\\jacob-1.18\\jacob-1.18-x64.dll";
        System.out.println(path1);
        
        System.setProperty("java.library.path", libPath);
//        Field sysPath;
//        try {
//            sysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
//            sysPath.setAccessible( true );
//            sysPath.set( null, null );
//            System.load(libPath);
//        } catch (NoSuchFieldException | SecurityException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        
        
//        System.load(libPath);
        
        
        System.out.println("START");
        String filePathWord = "C:\\Users\\zzhang\\Desktop\\Temp\\1.doc";
        String filePathExcel = "C:\\Users\\zzhang\\Desktop\\Temp\\1.xlsx";
        String filePathPPT = "C:\\Users\\zzhang\\Desktop\\Temp\\1.pptx";
//        office2PDF(filePathWord, "word");
        office2PDF(filePathExcel, "excel");
        office2PDF(filePathPPT, "powerpoint");
        System.out.println("END");
    }

    public static void office2PDF(String filePath, String fileType) {
        File srcFile = new File(filePath);
        Office2PDF pdf;
        try {
            pdf = Office2PDF.getInstance();
            pdf.convert2PDF(srcFile, fileType, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void covertToPdf(Integer type) {
        if (type == 1) {
            ConvertToPDF ctpdf = new ConvertToPDF(1);
            ctpdf.openDoc("C:\\Users\\zzhang\\Desktop\\Temp\\1.doc");
            ctpdf.publishAsPDF("C:\\Users\\zzhang\\Desktop\\Temp\\1.doc.pdf");
            ctpdf.closeDoc();
            ctpdf.quit();
        } else if (type == 2) {
            ConvertToPDF ctpdf = new ConvertToPDF(2);
            ctpdf.openDoc("C:\\Users\\zzhang\\Desktop\\Temp\\1.xlsx");
            ctpdf.publishAsPDF("C:\\Users\\zzhang\\Desktop\\Temp\\1.pdf");
            ctpdf.closeDoc();
            ctpdf.quit();
        } else {
            ConvertToPDF ctpdf = new ConvertToPDF(3);
            ctpdf.openDoc("C:\\Users\\zzhang\\Desktop\\Temp\\1.pptx");
            ctpdf.publishAsPDF("C:\\Users\\zzhang\\Desktop\\Temp\\1.pptx.pdf");
            ctpdf.closeDoc();
            ctpdf.quit();
        }
    }
}
