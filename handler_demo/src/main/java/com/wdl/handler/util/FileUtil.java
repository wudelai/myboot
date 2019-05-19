package com.wdl.handler.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * 文件操作相关
 *
 * @author
 */
public class FileUtil {
    /**
     * @param directory
     * @throws IOException
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (!isSymlink(directory)) {
                cleanDirectory(directory);
            }

            if (!directory.delete()) {
                String message = "Unable to delete directory " + directory + ".";
                throw new IOException(message);
            }
        }
    }

    /**
     * @param directory
     * @throws IOException
     */
    public static void cleanDirectory(File directory) throws IOException {
        String message;
        if (!directory.exists()) {
            message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        } else if (!directory.isDirectory()) {
            message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        } else {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IOException("Failed to list contents of " + directory);
            } else {
                IOException exception = null;
                File[] var3 = files;
                int var4 = files.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File file = var3[var5];

                    try {
                        forceDelete(file);
                    } catch (IOException var8) {
                        exception = var8;
                    }
                }

                if (null != exception) {
                    throw exception;
                }
            }
        }
    }

    /**
     * @param file
     * @throws IOException
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }

                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }

    }

    /**
     * @param file
     * @throws IOException
     */
    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }

    }

    /**
     * @param directory
     * @throws IOException
     */
    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (directory.exists()) {
            directory.deleteOnExit();
            if (!isSymlink(directory)) {
                cleanDirectoryOnExit(directory);
            }

        }
    }

    /**
     * @param directory
     * @throws IOException
     */
    private static void cleanDirectoryOnExit(File directory) throws IOException {
        String message;
        if (!directory.exists()) {
            message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        } else if (!directory.isDirectory()) {
            message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        } else {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IOException("Failed to list contents of " + directory);
            } else {
                IOException exception = null;
                File[] var3 = files;
                int var4 = files.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File file = var3[var5];

                    try {
                        forceDeleteOnExit(file);
                    } catch (IOException var8) {
                        exception = var8;
                    }
                }

                if (null != exception) {
                    throw exception;
                }
            }
        }
    }

    /**
     * @param directory
     * @throws IOException
     */
    public static void forceMkdir(File directory) throws IOException {
        String message;
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                message = "File " + directory + " exists and is not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            message = "Unable to create directory " + directory;
            throw new IOException(message);
        }

    }

    /**
     * @param file
     * @throws IOException
     */
    public static void forceMkdirParent(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null) {
            forceMkdir(parent);
        }
    }

    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        } else if (File.separatorChar == '\\') {
            return false;
        } else {
            File fileInCanonicalDir = null;
            if (file.getParent() == null) {
                fileInCanonicalDir = file;
            } else {
                File canonicalDir = file.getParentFile().getCanonicalFile();
                fileInCanonicalDir = new File(canonicalDir, file.getName());
            }

            return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
        }
    }


}
