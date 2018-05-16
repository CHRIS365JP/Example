package example.jacob;

import org.apache.commons.lang3.StringUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class TestConverter {
    public static void main(String[] args) {
        
//        System.setProperty("java.library.path", "C:/mylibs");
        String path = System.getProperty("java.library.path");
        path = StringUtils.substringBefore(path, ".;C:\\Work\\Develop\\STS.3.9.3\\workspace-STS.3.9.3\\ADownloadJARProject\\target\\classes;");
        if(StringUtils.isNoneEmpty(path)) {
            System.setProperty("java.library.path", path + ";C:\\Work\\Develop\\STS.3.9.3\\workspace-STS.3.9.3\\ADownloadJARProject\\target\\classes;.");
        }
        System.out.println(System.getProperty("java.library.path") + "");
        
        ActiveXComponent _app = new ActiveXComponent("Word.Application");
        _app.setProperty("Visible", new Variant(false));

        Dispatch documents = _app.getProperty("Documents").toDispatch();

        Dispatch doc = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[] {
                "C:\\Users\\zzhang\\Desktop\\Temp\\1.doc",
                new Variant(false),
                new Variant(true)
        }, new int[1]).toDispatch();

        Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                "C:\\Users\\zzhang\\Desktop\\Temp\\1.pdf",
                new Variant(false),
                new Variant(true)
        }, new int[1]);

        Variant f = new Variant(false);
        Dispatch.call(doc, "Close", f);
        _app.invoke("Quit", new Variant[] {});
        ComThread.Release();
    }
}
