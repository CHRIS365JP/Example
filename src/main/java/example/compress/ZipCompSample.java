package example.compress;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipCompSample {

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\#17\\test_files\\";
        // １．sampleComp.zipに書き込むZipArchiveOutputStreamを作成します
        ZipArchiveOutputStream os = new ZipArchiveOutputStream(new File(filePath + "sampleComp.zip"));
        // ２．中の圧縮ファイルに適用するエンコードを指定します。システム標準にしてみたのでWindowsならMS932になります。
        System.out.println(System.getProperty("file.encoding"));
        os.setEncoding("UTF-8");

        // ３．ZipArchiveEntry（Zipに入れるファイルのような物）を作成
        ZipArchiveEntry zae = new ZipArchiveEntry("文書.docx");
        // ４．ZipArchiveOutputStreamにエントリを入れます。
        os.putArchiveEntry(zae);
        File file = new File(filePath + "文書222.docx");
        os.putArchiveEntry(new ZipArchiveEntry("文書222.docx"));
        IOUtils.copy(new FileInputStream(file), os);
//        os.closeArchiveEntry();
        // ５．エントリの中身を書き込みます。
        // 中身が多い場合は、500バイトずつとか何回かに分けて書き込むと良いです。
//        os.write("文書.docx".getBytes());
        // ６．エントリをクローズします
        os.closeArchiveEntry();
        // 複数のファイルをzipファイルに入れる場合は、３～６の工程をファイルの数だけ繰り返します。

        // ７．最後にアウトプットストリームをクローズします。
        os.close();
        System.out.println("END");

        // これで、「ほげほげ.txt」の入った「sampleComp.zip」が出来上がったはずです。
    }
}