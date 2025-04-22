		addPropertyChangeListener(event -> {
			if (event.getSource() == fSchemeModel
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				changeScheme((SchemeElement) event.getOldValue(),
						(SchemeElement) event.getNewValue());
