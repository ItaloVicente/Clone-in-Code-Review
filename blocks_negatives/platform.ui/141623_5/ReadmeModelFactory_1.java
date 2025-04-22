                    for (int j = 0; j < configElements.length; j++) {
                        IConfigurationElement config = configElements[i];
                        if (config.getName()
                                .equals(IReadmeConstants.TAG_PARSER)) {
                            processParserElement(config);
                            break;
                        }
                    }
