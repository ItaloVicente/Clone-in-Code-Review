	private ModifyListener listener = new ModifyListener() {

		public void modifyText(ModifyEvent arg0) {
			ButtonElementProperties properties = (ButtonElementProperties) Adapters.adapt(buttonElement, IPropertySource.class);
			properties.setPropertyValue(ButtonElementProperties.PROPERTY_TEXT, labelText.getText());
		}
