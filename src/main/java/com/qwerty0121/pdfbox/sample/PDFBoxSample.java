package com.qwerty0121.pdfbox.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PDFBoxSample {

    public static void main(String[] args) {
        try (
                var src1 = getSource("pdf1.pdf");
                var src2 = getSource("pdf2.pdf");) {
            var merger = new PDFMergerUtility();
            merger.addSource(src1);
            merger.addSource(src2);
            merge(merger);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static RandomAccessReadBuffer getSource(String name) throws IOException {
        try (var is = PDFBoxSample.class.getClassLoader().getResourceAsStream(name);) {
            return new RandomAccessReadBuffer(is);
        }
    }

    private static void merge(PDFMergerUtility merger) {
        File outputFileDir = getOrCreateDestDir();

        var outputFilePath = outputFileDir.toPath().resolve("merged.pdf");
        try (var os = new FileOutputStream(outputFilePath.toString());) {
            merger.setDestinationStream(os);
            merger.mergeDocuments(IOUtils.createTempFileOnlyStreamCache());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static File getOrCreateDestDir() {
        String outputFileDirPath = "./.output/";
        File outputFileDir = new File(outputFileDirPath);
        outputFileDir.mkdir();
        return outputFileDir;
    }

}
