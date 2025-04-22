		IPropertyChangeListener dataUpdateListener = event -> {
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
				commandNameValueLabel.setText(""); //$NON-NLS-1$
				fDescriptionText.setText(""); //$NON-NLS-1$
				fBindingText.setText(""); //$NON-NLS-1$
			} else if (bindingElement != null) {
				commandNameValueLabel.setText(bindingElement.getName());
				String desc = bindingElement.getDescription();
				fDescriptionText.setText(desc==null?"":desc); //$NON-NLS-1$
				KeySequence trigger = (KeySequence) bindingElement
						.getTrigger();
				fKeySequenceText.setKeySequence(trigger);
