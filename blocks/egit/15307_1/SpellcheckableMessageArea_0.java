			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (property.equals(getPreferenceKey())
						|| AbstractTextEditor.PREFERENCE_SHOW_LEADING_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_TRAILING_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_LEADING_TABS
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_TABS
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_TRAILING_TABS
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_CARRIAGE_RETURN
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_SHOW_LINE_FEED
								.equals(property)
						|| AbstractTextEditor.PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE
								.equals(property)) {
					synchronizeWithPreference();
				}
			}

