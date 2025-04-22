        final IPropertyChangeListener fontListener = new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
                if (JFaceResources.HEADER_FONT.equals(event.getProperty())) {
                    messageLabel.setFont(JFaceResources.getHeaderFont());
                }
            }
        };
