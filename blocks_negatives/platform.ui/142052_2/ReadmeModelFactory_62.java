    /**
     * Loads the parser from the registry by searching for
     * extensions that satisfy our published extension point.
     * For the sake of simplicity, we will pick the last extension,
     * allowing tools to override what is used. In a more
     * elaborate tool, all the extensions would be processed.
     */
    private void loadParser() {
        IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
                IReadmeConstants.PLUGIN_ID, IReadmeConstants.PP_SECTION_PARSER);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (int i = 0; i < extensions.length; i++) {
                IExtension currentExtension = extensions[i];
                if (i == extensions.length - 1) {
                    IConfigurationElement[] configElements = currentExtension
                            .getConfigurationElements();
                    for (int j = 0; j < configElements.length; j++) {
                        IConfigurationElement config = configElements[i];
                        if (config.getName()
                                .equals(IReadmeConstants.TAG_PARSER)) {
                            processParserElement(config);
                            break;
                        }
                    }
                }
            }
        }
        if (parser == null)
            parser = new DefaultSectionsParser();
        registryLoaded = true;
    }
