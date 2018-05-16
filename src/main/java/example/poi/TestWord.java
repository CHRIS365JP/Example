package example.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class TestWord {
    
    public static void main(String[] args) {
        
        String outputFilePath = "out.docx";
        XWPFDocument document = null;
        XWPFTable table;
        XWPFParagraph paragraph;
        XWPFRun run;
        FileOutputStream fout = null;

        try {
            document = new XWPFDocument();

            //普通の段落を2つ作る
            for (int i = 0; i < 2; i++) {
                paragraph = document.createParagraph();

                //それぞれの段落の中に色の異なるテキストを2種配置する
                //setText内で\nを指定しても改行されないので注意、改行するには必ず段落を作る
                run = paragraph.createRun();
                run.setFontFamily("ＭＳ ゴシック");
                run.setText("黒のテキスト");

                run = paragraph.createRun();
                run.setFontFamily("ＭＳ ゴシック");
                run.setColor("ff0000");
                run.setText("赤のテキスト");
            }
            //2x2の表を作る
            table = document.createTable(2, 2);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    //それぞれのセルの中に段落を2つ作る
                    for (int k = 0; k < 2; k++) {
                        //セルには初期状態で1つの段落がある(実装が変わるかもしれないので念のため存在数を確認して適切に処理)
                        if (table.getRow(i).getCell(j).getParagraphs().size() > k) {
                            paragraph = table.getRow(i).getCell(j).getParagraphs().get(k);
                        }
                        else {
                            paragraph = table.getRow(i).getCell(j).addParagraph();
                        }

                        //それぞれの段落の中に色の異なるテキストを2種配置する
                        run = paragraph.createRun();
                        run.setFontFamily("ＭＳ ゴシック");
                        run.setText("黒のテキスト");

                        run = paragraph.createRun();
                        run.setFontFamily("ＭＳ ゴシック");
                        run.setColor("ff0000");
                        run.setText("赤のテキスト");
                    }
                }
            }

            //ファイル出力
            fout = new FileOutputStream(outputFilePath);
            document.write(fout);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (fout != null) {
                try {
                    fout.close();
                }
                catch (IOException e) {
                }
            }
            if (document != null) {
                try {
                    document.close();
                }
                catch (IOException e) {
                }
            }
        }
    }
}
