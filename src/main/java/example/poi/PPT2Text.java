package example.poi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hslf.record.Slide;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.TextBox;

public class PPT2Text {

    public static void main(String[] args) {

        if (args.length != 1) {
            printUsage();
            System.exit(1);
        }

        HSLFSlideShow slideShow = new HSLFSlideShow();
        List<HSLFSlide> slides = slideShow.getSlides();

        for (HSLFSlide slide : slides) {

            List<HSLFShape> shapes = slide.getShapes();

            for (int j = 0; j < shapes.size(); j++) {

                if (shapes.get(j) instanceof TextBox) {
                    TextBox shape = (TextBox) shapes.get(j);
                    String text = shape.getText();
                    if (text != null) {
                        System.out.println(text);
                    }
                }
            }
        }
    }

    public static void printUsage() {
        System.out.println("Usage: ppt2text filename");
    }

}
