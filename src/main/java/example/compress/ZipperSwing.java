package example.compress;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.compress.archivers.ArchiveException;

public class ZipperSwing {
    public static void main(String[] args) throws ArchiveException, IOException {
        new ZipperSwing();
    }

    private JFrame frame = new JFrame("Zipper");
    private JTextField baseDirForZip = new JTextField("");
    private JTextField zipFileNameForZip = new JTextField("");
    private JTextField encordForZip = new JTextField("Windows-31J");
    private JTextField baseDirForUnzip = new JTextField("");
    private JTextField zipFileNameForUnzip = new JTextField("");
    private JTextField encordForUnzip = new JTextField("Windows-31J");

    private ZipperSwing() {
        JTabbedPane tab = new JTabbedPane();
        tab.add("圧縮", createZipPanel());
        tab.add("解凍", createUnzipPanel());
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(tab);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    private JPanel createZipPanel() {
        JPanel zipPanel = new JPanel();

        zipPanel.setLayout(new BorderLayout());

        {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));

            panel.add(new JLabel("圧縮対象のディレクトリまたはフォルダ"));
            panel.add(zipFileNameForZip);
            panel.add(new JLabel("作成するZIPファイル名"));
            panel.add(baseDirForZip);
            panel.add(new JLabel("ZIPファイルのファイル名のエンコード"));
            panel.add(encordForZip);
            zipPanel.add(panel, BorderLayout.CENTER);
        }

        JButton execBtn = new JButton("実行");
        execBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zip();
            }
        });

        zipPanel.add(execBtn, BorderLayout.SOUTH);

        return zipPanel;
    }

    private JPanel createUnzipPanel() {
        JPanel zipPanel = new JPanel();

        zipPanel.setLayout(new BorderLayout());

        {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));

            panel.add(new JLabel("ZIPファイル"));
            panel.add(baseDirForUnzip);
            panel.add(new JLabel("解凍先のディレクトリ"));
            panel.add(zipFileNameForUnzip);
            panel.add(new JLabel("ZIPファイルのファイル名のエンコード"));
            panel.add(encordForUnzip);
            zipPanel.add(panel, BorderLayout.CENTER);
        }

        JButton execBtn = new JButton("実行");
        execBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unzip();
            }
        });

        zipPanel.add(execBtn, BorderLayout.SOUTH);

        return zipPanel;
    }

    private void zip() {
        String baseDir = baseDirForZip.getText();
        String zipFileName = zipFileNameForZip.getText();
        String encord = encordForZip.getText();
        try {
            Zipper.createZipFile(new File(baseDir), new File(zipFileName), encord);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, e.getMessage(), "err", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "finish");
    }

    private void unzip() {
        String baseDir = baseDirForUnzip.getText();
        String zipFileName = zipFileNameForUnzip.getText();
        String encord = encordForUnzip.getText();
        try {
            Zipper.unpackingZipFile(new File(baseDir), new File(zipFileName), encord);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, e.getMessage(), "err", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "finish");
    }
}
