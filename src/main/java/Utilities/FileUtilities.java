package Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import services.EventServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtilities {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilities.class);
    private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    public static List<File> getAllXmlFiles() {
        String pattern = "classpath*:static/event XML/*.xml"; // Define the pattern to match XML files in static/eventXML

        // Retrieve resources matching the pattern
        Resource[] resources = new Resource[0];
        try {
            resources = resourcePatternResolver.getResources(pattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Collect all XML files in a list
        List<File> xmlFiles = new ArrayList<>();
        for (Resource resource : resources) {
            try {
                // Only add if the resource can be converted to a File (i.e., not in a JAR)
                if (resource.exists() && resource.isFile()) {
                    File file = resource.getFile();
                    xmlFiles.add(file);
                }
            } catch (IOException e) {
                // Handle case where the resource cannot be converted to a File
               logger.error("Could not load resource as file: " + resource.getDescription());
            }
        }
        return xmlFiles;
    }


}
