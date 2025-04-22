                                getColorRegistry().put(colorDefinition.getId(), rgb);
                                processDefaultsTo(colorDefinition.getId(), rgb);
                            }
                        }
                    }
                }
            };
        }
        return propertyListener;
    }

    /**
     * Listener that is responsible for rebroadcasting events fired from the base font/color registry
     */
    private IPropertyChangeListener getCascadeListener() {
        if (themeListener == null) {
            themeListener = event -> firePropertyChange(event);
        }
        return themeListener;
    }

    @Override
