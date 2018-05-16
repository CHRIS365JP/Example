package example.compress;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class Zipper {
    public static void createZipFile(File zipFile, File baseDir) throws ArchiveException, IOException {
        createZipFile(zipFile, baseDir, null);
    }

    public static void createZipFile(File zipFile, File baseDir, String encording) throws ArchiveException, IOException {
        Zipper zipper = new Zipper(encording);
        zipper.zip(zipFile, baseDir);
    }

    public static void unpackingZipFile(File zipFile, File baseDir) throws ArchiveException, IOException {
        unpackingZipFile(zipFile, baseDir, null);
    }

    public static void unpackingZipFile(File zipFile, File baseDir, String encording) throws ArchiveException, IOException {
        Zipper zipper = new Zipper(encording);
        zipper.unzip(zipFile, baseDir);
    }

    private String encording = null;

    public Zipper() {
    }

    public Zipper(String encording) {
        this.encording = encording;
    }

    public void zip(File zipFile, File baseDir) throws ArchiveException, IOException {
        if (zipFile == null) {
            throw new NullPointerException("zipFile is null");
        }

        if (baseDir == null) {
            throw new NullPointerException("baseDir is null");
        }

        if (!baseDir.exists()) {
            throw new NullPointerException("[" + baseDir + "] is nothing");
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(zipFile));
        ZipArchiveOutputStream archive = new ZipArchiveOutputStream(out);
        if (encording != null) {
            archive.setEncoding("Windows-31J");
        }

        String basePath = baseDir.getAbsolutePath();
        addAll(archive, basePath, baseDir);

        archive.finish();
        archive.flush();
        archive.close();

        out.flush();
    }

    private void addAll(ArchiveOutputStream archive, String basePath, File targetFile) throws IOException {
        if (targetFile.isDirectory()) {
            File[] children = targetFile.listFiles();

            if (children.length == 0) {
                addDir(archive, basePath, targetFile);
            } else {
                for (File file : children) {
                    addAll(archive, basePath, file);
                }
            }

        } else {
            addFile(archive, basePath, targetFile);
        }

    }

    private void addFile(ArchiveOutputStream archive, String basePath, File file) throws IOException {
        String path = file.getAbsolutePath();
        String name = path.substring(basePath.length());

        archive.putArchiveEntry(new ZipArchiveEntry(name));
        IOUtils.copy(new FileInputStream(file), archive);
        archive.closeArchiveEntry();
    }

    private void addDir(ArchiveOutputStream archive, String basePath, File file) throws IOException {
        String path = file.getAbsolutePath();
        String name = path.substring(basePath.length());

        archive.putArchiveEntry(new ZipArchiveEntry(name + "/"));
        archive.closeArchiveEntry();
    }

    public void unzip(File zipFile, File baseDir) throws IOException {
        if (baseDir == null) {
            throw new NullPointerException("baseDir is null");
        }

        if (baseDir.exists() && !baseDir.isDirectory()) {
            throw new NullPointerException("[" + baseDir + "] is not directory");
        }
        baseDir.mkdirs();

        String baseDirPath = baseDir.getAbsolutePath();
        System.out.println("baseDirPath=" + baseDirPath);
        final InputStream is = new FileInputStream(zipFile);

        ZipArchiveInputStream archive;
        if (encording == null) {
            archive = new ZipArchiveInputStream(is);
        } else {
            archive = new ZipArchiveInputStream(is, encording, true);
        }

        ZipArchiveEntry entry;
        while ((entry = archive.getNextZipEntry()) != null) {
            File file = new File(baseDirPath + entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                IOUtils.copy(archive, out);
                out.close();
            }
        }

        archive.close();

    }
}
