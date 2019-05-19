package com.wdl.handler.util;

import java.io.*;

public class IOUtil {
    public static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public IOUtil() {
    }


    /**
     * 关闭 流
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
            ;
        }

    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count;
        try {
            count = copyLarge(input, output);
        }finally {
            closeQuietly(input);
            closeQuietly(output);
        }
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        long count = 0L;
        byte[] buffer = new byte[4096];
        int n;
        for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        } else {
            int remaining;
            int count;
            for (remaining = length; remaining > 0; remaining -= count) {
                int location = length - remaining;
                count = input.read(buffer, offset + location, remaining);
                if (-1 == count) {
                    break;
                }
            }

            return length - remaining;
        }
    }

    public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        int actual = read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    public static void readFully(InputStream input, byte[] buffer) throws IOException {
        readFully(input, buffer, 0, buffer.length);
    }
}
