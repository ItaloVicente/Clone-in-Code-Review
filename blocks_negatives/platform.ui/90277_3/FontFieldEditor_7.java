                    FontData font = fontDialog.open();
                    if (font != null) {
                        FontData[] oldFont = chosenFont;
                        if (oldFont == null) {
							oldFont = JFaceResources.getDefaultFont()
                                    .getFontData();
						}
                        setPresentsDefaultValue(false);
                        FontData[] newData = new FontData[1];
                        newData[0] = font;
                        updateFont(newData);
                        fireValueChanged(VALUE, oldFont[0], font);
                    }

                }
            });
