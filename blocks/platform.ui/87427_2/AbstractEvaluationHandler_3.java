			enablementListener = event -> {
				if (event.getProperty() == PROP_ENABLED) {
					if (event.getNewValue() instanceof Boolean) {
						setEnabled(((Boolean) event.getNewValue())
								.booleanValue());
					} else {
						setEnabled(false);
