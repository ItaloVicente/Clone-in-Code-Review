		limitText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				try {
					Integer.parseInt(limitText.getText());
				} catch (NumberFormatException ex) {
					limitText.setText(Integer.toString(generator.getMarkerLimits()));
				}
