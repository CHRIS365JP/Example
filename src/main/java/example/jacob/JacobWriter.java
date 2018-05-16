package example.jacob;

import org.junit.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWriter {
    private ActiveXComponent app;
    private Dispatch documents = null;
    private Dispatch doc = null;
    private String filepath = null;
    private boolean isWord = true; //true：是Word；flase：是Excel

    public JacobWriter(boolean isWord) throws Exception {
        this.isWord = isWord;
        if (isWord) {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false)); //设置word不可见
            documents = app.getProperty("Documents").toDispatch();
        } else {
            app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", new Variant(false)); //设置word不可见
            documents = app.getProperty("Workbooks").toDispatch();
        }
    }

    /**
     * 打开Word文档
     * 
     * @param docFilePath
     * @param readOnly 是否以只读方式打开
     * @throws Exception
     */
    public void openWord(String docFilePath, boolean readOnly) throws Exception {
        try {
            filepath = docFilePath;
//          doc = Dispatch.call(documents, "Open", filepath).toDispatch();//也可替换为doc = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[]{inFile, new Variant(false), new Variant(false)}, new int[1]).toDispatch();   //打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，因为我们要保存原文件，所以以可写方式打开。
            doc = Dispatch.invoke( //打开并获取Excel文档
                    documents, "Open", Dispatch.Method, new Object[] {
                            filepath,
                            new Variant(false),
                            new Variant(readOnly) //是否以只读方式打开:是——只读方式打开
                    }, new int[1]).toDispatch();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 另存为Pdf
     * 
     * @param filePath: 另存为的路径
     */
    public void saveAsPdf(String filePath) {
        if (isWord) {
            if (doc != null) {
                Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                        filePath,
                        new Variant(17)
                }, new int[1]); //17表示另存为PDF格式
            }
        } else {
            if (doc != null) { //转换Excel当前活动页为PDF
                Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                        filePath,
                        new Variant(57),
                        new Variant(false),
                        new Variant(57),
                        new Variant(57),
                        new Variant(false),
                        new Variant(true),
                        new Variant(57),
                        new Variant(true),
                        new Variant(true),
                        new Variant(true)
                }, new int[1]);
            }
        }

    }

    /**
     * 关闭文档
     * 
     * @param f：为true时保存修改的文件并退出；为false时不保存修改的文件并退出
     */
    public void closeWord(boolean f) {
        if (doc != null) {
            Dispatch.call(doc, "Close", new Variant(f));
            doc = null;
        }
    }

    /**
     * 释放资源
     */
    public void releaseResourse() {
        documents = null;

        // 释放 Com 资源
        if (app != null) {
            app.invoke("Quit", new Variant[] {});//或者使用方法：Dispatch.call(app, "Quit");
            app = null;
        }
    }

    @Test
    public void testWordToPdf() {
        String filePath = "C:\\Users\\zzhang\\Desktop\\Temp\\1.doc";

        JacobWriter jw;
        try {
            jw = new JacobWriter(true);
            jw.saveAsPdf(filePath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}