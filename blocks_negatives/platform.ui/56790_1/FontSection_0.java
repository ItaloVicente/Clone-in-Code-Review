                    ButtonElementProperties properties = (ButtonElementProperties) buttonElement
                        .getAdapter(IPropertySource.class);
                    properties.setPropertyValue(
                        ButtonElementProperties.PROPERTY_FONT, value);
                    fontText.setText(StringConverter.asString(fData));
                }
