                    File disabledSource = new File(setParent, disabledVariant);

                    String path = rootUri.relativize(
                    		disabledSource.toURI()).getPath();

                    disabledOutputDir = new File(outputBase, path);
                    if(!disabledOutputDir.exists()) {
                    	disabledOutputDir.mkdirs();
                    }
