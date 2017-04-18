package com.imergo.etl.helpers;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;

/**
 * Created by korovin on 4/18/2017.
 */
public class ResourceHelper {
    public static InputStream getResource(String filePath, ClassLoader classLoader) throws NoSuchFileException
    {
        if (filePath.startsWith("/"))
        {
            throw new IllegalArgumentException("Relative paths may not have a leading slash!");
        }

        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        if (inputStream == null)
        {
            throw new NoSuchFileException("Resource file not found. Note that the current directory is the source folder!");
        }

        return inputStream;
    }
}
