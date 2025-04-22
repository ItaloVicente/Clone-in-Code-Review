	private ModifyListener listener = new ModifyListener() {

		public void modifyText(ModifyEvent arg0) {
			ButtonElementProperties properties = (ButtonElementProperties) Adapters.adapt(buttonElement, IPropertySource.class);
			SizePropertySource sizePropertySource = (SizePropertySource) properties
					.getPropertyValue(ButtonElementProperties.PROPERTY_SIZE);
			sizePropertySource.setPropertyValue(SizePropertySource.ID_HEIGHT,
				heightText.getText());
			sizePropertySource.setPropertyValue(SizePropertySource.ID_WIDTH,
				widthText.getText());
		}
