package com.sismics.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import com.sismics.BaseTest;
import com.sismics.util.mime.MimeType;
import com.sismics.util.mime.MimeTypeUtil;

/**
 * Test of the utilities to check MIME types.
 * 
 * @author bgamard
 */
public class TestMimeTypeUtil extends BaseTest {
    @Test
    public void test() throws Exception {
        // Detect ODT files
        Path path = Paths.get(getResource(FILE_ODT).toURI());
        Assert.assertEquals(MimeType.OPEN_DOCUMENT_TEXT, MimeTypeUtil.guessMimeType(path, FILE_ODT));

        // Detect DOCX files
        path = Paths.get(getResource(FILE_DOCX).toURI());
        Assert.assertEquals(MimeType.OFFICE_DOCUMENT, MimeTypeUtil.guessMimeType(path, FILE_ODT));

        // Detect PPTX files
        path = Paths.get(getResource(FILE_PPTX).toURI());
        Assert.assertEquals(MimeType.OFFICE_PRESENTATION, MimeTypeUtil.guessMimeType(path, FILE_PPTX));

        // Detect XLSX files
        path = Paths.get(getResource(FILE_XLSX).toURI());
        Assert.assertEquals(MimeType.OFFICE_SHEET, MimeTypeUtil.guessMimeType(path, FILE_XLSX));

        // Detect TXT files
        path = Paths.get(getResource(FILE_TXT).toURI());
        Assert.assertEquals(MimeType.TEXT_PLAIN, MimeTypeUtil.guessMimeType(path, FILE_TXT));

        // Detect CSV files
        path = Paths.get(getResource(FILE_CSV).toURI());
        String csvMimeType = MimeTypeUtil.guessMimeType(path, FILE_CSV);
        Assert.assertTrue(
                "Unexpected CSV MIME type: " + csvMimeType,
                MimeType.TEXT_CSV.equals(csvMimeType)
                        || "application/vnd.ms-excel".equals(csvMimeType));

        // Detect PDF files
        path = Paths.get(getResource(FILE_PDF).toURI());
        Assert.assertEquals(MimeType.APPLICATION_PDF, MimeTypeUtil.guessMimeType(path, FILE_PDF));

        // Detect JPEG files
        path = Paths.get(getResource(FILE_JPG).toURI());
        Assert.assertEquals(MimeType.IMAGE_JPEG, MimeTypeUtil.guessMimeType(path, FILE_JPG));

        // Detect GIF files
        path = Paths.get(getResource(FILE_GIF).toURI());
        Assert.assertEquals(MimeType.IMAGE_GIF, MimeTypeUtil.guessMimeType(path, FILE_GIF));

        // Detect PNG files
        path = Paths.get(getResource(FILE_PNG).toURI());
        Assert.assertEquals(MimeType.IMAGE_PNG, MimeTypeUtil.guessMimeType(path, FILE_PNG));

       // Detect ZIP files
        path = Paths.get(getResource(FILE_ZIP).toURI());
        String zipMimeType = MimeTypeUtil.guessMimeType(path, FILE_ZIP);
        Assert.assertTrue(
                "Unexpected ZIP MIME type: " + zipMimeType,
                MimeType.APPLICATION_ZIP.equals(zipMimeType)
                        || "application/x-zip-compressed".equals(zipMimeType));

        // Detect WEBM files
        path = Paths.get(getResource(FILE_WEBM).toURI());
        Assert.assertEquals(MimeType.VIDEO_WEBM, MimeTypeUtil.guessMimeType(path, FILE_WEBM));

        // Detect MP4 files
        path = Paths.get(getResource(FILE_MP4).toURI());
        Assert.assertEquals(MimeType.VIDEO_MP4, MimeTypeUtil.guessMimeType(path, FILE_MP4));
    }

    @Test
    public void testGuessMimeTypeWithWrongFileName() throws Exception {
        // The content is a JPEG image, but the file name has an unknown extension.
        // This checks that MIME detection can still rely on file content.
        Path path = Paths.get(getResource(FILE_JPG).toURI());
        Assert.assertEquals(MimeType.IMAGE_JPEG, MimeTypeUtil.guessMimeType(path, "unknown-file.unknown"));
    }

    @Test
    public void testGuessMimeTypeWithUppercaseExtension() throws Exception {
        // The content is a PNG image, but the extension is uppercase.
        // This checks case-insensitive extension handling.
        Path path = Paths.get(getResource(FILE_PNG).toURI());
        Assert.assertEquals(MimeType.IMAGE_PNG, MimeTypeUtil.guessMimeType(path, "IMAGE.PNG"));
    }

    @Test
    public void testGuessMimeTypeWithNoExtension() throws Exception {
        // The content is a PDF file, but the file name has no extension.
        // This checks the fallback/content-based branch.
        Path path = Paths.get(getResource(FILE_PDF).toURI());
        Assert.assertEquals(MimeType.APPLICATION_PDF, MimeTypeUtil.guessMimeType(path, "file_without_extension"));
    }

    @Test
    public void testGuessMimeTypeWithEmptyFile() throws Exception {
        Path path = Files.createTempFile("empty-file", ".txt");

        try {
            Assert.assertEquals(MimeType.TEXT_PLAIN, MimeTypeUtil.guessMimeType(path, "empty-file.txt"));
        } finally {
            Files.deleteIfExists(path);
        }
    }

}
