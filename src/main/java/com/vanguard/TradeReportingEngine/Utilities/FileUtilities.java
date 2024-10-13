package com.vanguard.TradeReportingEngine.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileUtilities {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilities.class);
    private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    public static List<File> getAllXmlFiles() {
        String pattern = "classpath*:static/event XML/*.xml"; // Define the pattern to match XML files in static/eventXML
        List<File> files=null;
        try {
            files = listFilesFromClasspath(pattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }
    // List files from classpath using streams (compatible with both JAR and local)
    public static List<File> listFilesFromClasspath(String directoryPath) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(directoryPath);
        resources[0].getFilename();
        return Arrays.stream(resources)
                .map(FileUtilities::resourceToTempFile)  // Convert resources to temp files
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Convert Resource to a temporary File
    private static File resourceToTempFile(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            File tempFile = File.createTempFile(Objects.requireNonNull(resource.getFilename()), null);
            tempFile.deleteOnExit();

            try (OutputStream os = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            logger.error("Failed to process resource: " + resource.getDescription());
            return null;
        }
    }

}

