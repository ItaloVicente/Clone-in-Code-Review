			IPropertyChangeListener resultListener = null;
			if (callback != null) {
				resultListener = event -> {
					if (event.getProperty().equals(IAction.RESULT)) {
						if (event.getNewValue() instanceof Boolean) {
							result = (Boolean) event.getNewValue();
