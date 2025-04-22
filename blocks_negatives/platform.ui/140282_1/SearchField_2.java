			@Override
			protected void doClose() {
				resetProviders();
				dialogHeight = shell.getSize().y;
				dialogWidth = shell.getSize().x;
				shell.setVisible(false);
				removeAccessibleListener();
			}
