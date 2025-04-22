			hyperlinkChangeListener = event -> {
				String property = event.getProperty();
				if (preferenceKeysForEnablement.contains(property)) {
					resetHyperlinkDetectors();
					async(() -> refresh());
				} else if (preferenceKeysForActivation.contains(property)) {
					resetHyperlinkDetectors();
