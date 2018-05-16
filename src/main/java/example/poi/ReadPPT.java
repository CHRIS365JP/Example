package example.poi;

import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.sl.usermodel.PlaceableShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFConnectorShape;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.xmlbeans.XmlObject;

public class ReadPPT {
    static String read(InputStream is) throws Exception {

        System.out.println(FileMagic.valueOf(is));

        String text = "";

        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
//            WordExtractor ex = new WordExtractor(is);
//            text = ex.getText();
//            ex.close();
        } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
            XMLSlideShow ppt = new XMLSlideShow(is);
            XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
//            XWPFDocument doc = new XWPFDocument(is);
//            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//            text = extractor.getText();
//            extractor.close();
            for (XSLFSlide slide : ppt.getSlides()) {
                System.out.println("slide number:" + slide.getSlideNumber());
                System.out.println("slide title:" + slide.getTitle());
                for (XSLFShape sh : slide.getShapes()) {
                    // name of the shape
                    String name = sh.getShapeName();
                    XmlObject obj = sh.getXmlObject();
                    System.out.println("ShapeName:" + name);
                    // shapes's anchor which defines the position of this shape in the slide
                    if (sh instanceof PlaceableShape) {
                        Rectangle2D anchor = ((PlaceableShape)sh).getAnchor();
                    }

                    if (sh instanceof XSLFConnectorShape) {
                        XSLFConnectorShape line = (XSLFConnectorShape) sh;
                        // work with Line
                    } else if (sh instanceof XSLFTextShape) {
                        XSLFTextShape shape = (XSLFTextShape) sh;
                        System.out.println(shape.getText());
                        // work with a shape that can hold text
                    } else if (sh instanceof XSLFPictureShape) {
                        XSLFPictureShape shape = (XSLFPictureShape) sh;
                        // work with Picture
                    }
                }
            }
                    
        }
        return text;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\1.pptx";
        String filePath2 = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\新規 Microsoft PowerPoint プレゼンテーション.pptx";
        InputStream is = new BufferedInputStream(new FileInputStream(filePath2)); //really a binary OLE2 Word file
        System.out.println(read(is));
        is.close();
    }
}
