			} else if (BindingModel.PROP_BINDING_ADD.equals(event
					.getProperty())) {
				viewer.add(keyController.getBindingModel(), event
						.getNewValue());
			} else if (BindingModel.PROP_BINDING_REMOVE.equals(event
					.getProperty())) {
