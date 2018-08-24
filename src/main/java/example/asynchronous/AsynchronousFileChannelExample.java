package example.asynchronous;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsynchronousFileChannelExample {
    public static void main(String[] args) {

        String str = "";
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter("C:\\Work\\java.txt");
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            //ファイルに書き込む
            pw.println("apple");
            pw.println("orange");
            pw.println(str);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = "";
        writeFile(content);
    }

    private static long byteLen = 0;

    private static void writeFile(String tempTextContent) {
        String logFileName = "C:\\Work\\java2.txt";
        File logFile = new File(logFileName);
        AsynchronousFileChannel fileChannel = null;

        File filePath = logFile.getParentFile();

        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        try {
            if (!logFile.exists()) {

                logFile.createNewFile();
            }

            // ファイルの内容サイズ取得
            fileChannel = AsynchronousFileChannel.open(Paths.get(logFileName), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

            fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    byteLen = result.longValue();
                    if (byteLen == -1) {
                        byteLen = 0;
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            fileChannel = AsynchronousFileChannel.open(Paths.get(logFileName), StandardOpenOption.WRITE);
            long position = 0;
            position = position + byteLen;

            buffer.put(tempTextContent.getBytes("UTF-8"));
            buffer.flip();
            fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    byteLen = result.longValue();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
