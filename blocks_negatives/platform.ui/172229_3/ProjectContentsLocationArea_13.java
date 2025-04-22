			if (existingProject == null) {
				locationPathField.setText(IDEResourceInfoUtils.EMPTY_STRING);
			} else {
				locationPathField.setText(TextProcessor.process(existingProject
						.getLocation().toOSString()));
			}
