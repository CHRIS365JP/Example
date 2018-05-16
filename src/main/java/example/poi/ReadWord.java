package example.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;

public class ReadWord {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\1.docx";
        InputStream fIStream = null;
        try {
            fIStream = new FileInputStream(filePath);
//            InputStreamReader iSReader = new InputStreamReader(fIStream, "UTF-8");
//
//            int data;
//            while ((data = iSReader.read()) != -1) {
//                System.out.println(data);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // クラスパス上に配置したWordファイルを読み込む。
        HWPFDocument document = new HWPFDocument(fIStream);

        // ドキュメント全体を読み込む。
        Range text = document.getRange();

        IntStream.range(0, text.numSections()).forEach(sNo -> {
            Section section = text.getSection(sNo);

            IntStream.range(0, section.numParagraphs()).forEach(pNo -> {
                Paragraph paragraph = section.getParagraph(pNo);

                IntStream.range(0, paragraph.numCharacterRuns()).forEach(cNo -> {
                    CharacterRun characterRun = paragraph.getCharacterRun(cNo);

                    System.out.printf("%d:%d:%d:%s", sNo, pNo, cNo, characterRun.text());
                    System.out.println();

                });
            });
        });

    }
}
