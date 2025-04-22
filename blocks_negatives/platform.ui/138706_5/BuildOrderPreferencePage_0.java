                Text text = getTextControl();
                if (text != null) {
                    int value = ResourcesPlugin.getPlugin()
                            .getPluginPreferences().getDefaultInt(
                                    ResourcesPlugin.PREF_MAX_BUILD_ITERATIONS);
                    text.setText(Integer.toString(value));
                }
                valueChanged();
            }
