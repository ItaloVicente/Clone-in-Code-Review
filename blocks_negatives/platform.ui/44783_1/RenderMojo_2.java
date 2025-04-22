    /**
     * <p>Search the root resources directory for svg icons and add them to our
     * collection for rasterization later.</p>
     *
     * @param outputName
     * @param iconDir
     * @param outputBase
     * @param outputDir2
     */
    public void gatherIcons(String outputName, File rootDir, File iconDir,
            File outputBase) {

        File[] listFiles = iconDir.listFiles();

        for (File child : listFiles) {
            if (child.isDirectory()) {
                gatherIcons(outputName, rootDir, child, outputBase);
                continue;
            }

            if (!child.getName().endsWith("svg")) {
                return;
            }

            URI rootUri = rootDir.toURI();
            URI iconUri = iconDir.toURI();

            String relativePath = rootUri.relativize(iconUri).getPath();
            File outputDir = new File(outputBase, relativePath);
            File disabledOutputDir = null;

            File parentFile = child.getParentFile();

            /* Determine if/where to put a disabled version of the icon
               Eclipse traditionally uses a prefix of d for disabled, e for
               enabled in the folder name */
            if (parentFile != null) {
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

