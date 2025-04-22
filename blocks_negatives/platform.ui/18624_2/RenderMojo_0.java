                    for (File disabledFolder : setParent.listFiles()) {
                        if (disabledFolder.getName()
                                .equals(disabledVariant)) {
                            String path = rootUri.relativize(
                                    disabledFolder.toURI()).getPath();
                            disabledOutputDir = new File(outputBase, path);
                        }
                    }
