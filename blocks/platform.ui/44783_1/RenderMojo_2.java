
package org.eclipse.ui.images.renderer;

import java.io.File;
import java.net.URI;
import java.util.List;

public class IconGatherer {

    public static void gatherIcons(List<IconEntry> icons, String extension, File rootDir, File iconDir, File outputBase, boolean generateDisabledDirs) {
        File[] listFiles = iconDir.listFiles();

        for (File child : listFiles) {
            if (child.isDirectory()) {
            	if(child.getName().startsWith("d")) {
            		continue;
            	}
            	
                gatherIcons(icons, extension, rootDir, child, outputBase, generateDisabledDirs);
                continue;
            }

            if (!child.getName().endsWith(extension)) {
                continue;
            }

            URI rootUri = rootDir.toURI();
            URI iconUri = iconDir.toURI();

            String relativePath = rootUri.relativize(iconUri).getPath();
            File outputDir = new File(outputBase, relativePath);
            File disabledOutputDir = null;

            File parentFile = child.getParentFile();

               Eclipse traditionally uses a prefix of d for disabled, e for
               enabled in the folder name */
            if (generateDisabledDirs && parentFile != null) {
                String parentDirName = parentFile.getName();
                if (parentDirName.startsWith("e")) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("d");
                    builder.append(parentDirName.substring(1, parentDirName.length()));

                    String disabledVariant = builder.toString();

                    File setParent = parentFile.getParentFile();

                    File disabledSource = new File(setParent, disabledVariant);

                    String path = rootUri.relativize(
                              disabledSource.toURI()).getPath();

                    disabledOutputDir = new File(outputBase, path);
                    if(!disabledOutputDir.exists()) {
                        disabledOutputDir.mkdirs();
                    }
                }
            }

            IconEntry icon = createIcon(child, outputDir, disabledOutputDir);

            icons.add(icon);
        }
    }
    
    public static IconEntry createIcon(File input, File outputPath, File disabledPath) {
        String name = input.getName();
        String[] split = name.split("\\.(?=[^\\.]+$)");

        IconEntry def = new IconEntry(split[0], input, outputPath, disabledPath, new int[0]);

        return def;
    }

}
