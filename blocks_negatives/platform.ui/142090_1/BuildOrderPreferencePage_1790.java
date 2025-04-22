                Text text = getTextControl();
                if (text != null) {
                    int value = getWorkspace().getDescription()
                            .getMaxBuildIterations();
                    text.setText(Integer.toString(value));
                }
            }

            @Override
