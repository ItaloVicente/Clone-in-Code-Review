		IPropertyChangeListener dataUpdateListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				BindingElement bindingElement = null;
				boolean weCare = false;
				if (event.getSource() == keyController.getBindingModel()
						&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
								.getProperty())) {
					bindingElement = (BindingElement) event.getNewValue();
					weCare = true;
				} else if (event.getSource() == keyController.getBindingModel()
						.getSelectedElement()
						&& ModelElement.PROP_MODEL_OBJECT.equals(event
								.getProperty())) {
					bindingElement = (BindingElement) event.getSource();
					weCare = true;
				}
				if (bindingElement == null && weCare) {
				} else if (bindingElement != null) {
					commandNameValueLabel.setText(bindingElement.getName());
					String desc = bindingElement.getDescription();
					KeySequence trigger = (KeySequence) bindingElement
							.getTrigger();
					fKeySequenceText.setKeySequence(trigger);
				}
