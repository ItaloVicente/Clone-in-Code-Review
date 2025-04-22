		final IPropertyChangeListener fontListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (JFaceResources.BANNER_FONT.equals(event.getProperty())) {
					updateMessage();
				}
				if (JFaceResources.DIALOG_FONT.equals(event.getProperty())) {
					updateMessage();
					Font dialogFont = JFaceResources.getDialogFont();
					updateTreeFont(dialogFont);
					Control[] children = ((Composite) buttonBar).getChildren();
					for (int i = 0; i < children.length; i++) {
						children[i].setFont(dialogFont);
					}
