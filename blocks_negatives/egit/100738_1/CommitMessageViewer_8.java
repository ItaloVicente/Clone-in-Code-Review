		listener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL
						.equals(property)) {
					setFill(((Boolean) event.getNewValue()).booleanValue());
				} else
					if (UIPreferences.HISTORY_SHOW_TAG_SEQUENCE.equals(property)
							|| UIPreferences.HISTORY_SHOW_BRANCH_SEQUENCE
									.equals(property)
							|| UIPreferences.DATE_FORMAT.equals(property)
							|| UIPreferences.DATE_FORMAT_CHOICE
									.equals(property)) {
					format();
				}
