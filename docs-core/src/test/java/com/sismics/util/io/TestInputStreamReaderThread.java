package com.sismics.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of {@link InputStreamReaderThread}.
 */
public class TestInputStreamReaderThread {
    @Test
    public void testReadInputStream() throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("line1\nline2\n".getBytes("UTF-8"));
        InputStreamReaderThread thread = new InputStreamReaderThread(inputStream, "test");

        Assert.assertEquals("test InputStreamReader thread", thread.getName());

        thread.start();
        thread.join(1000);

        Assert.assertFalse(thread.isAlive());
    }

    @Test
    public void testIOExceptionIsIgnored() {
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Test exception");
            }
        };

        InputStreamReaderThread thread = new InputStreamReaderThread(inputStream, "error");

        thread.run();

        Assert.assertEquals("error InputStreamReader thread", thread.getName());
    }
}