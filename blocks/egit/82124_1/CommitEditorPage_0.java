			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)) {
					userText.setText(MessageFormat.format(
							author ? UIText.CommitEditorPage_LabelAuthor
									: UIText.CommitEditorPage_LabelCommitter,
							person.getName(), person.getEmailAddress(),
							PreferenceBasedDateFormatter.create()
									.formatDate(person)));
					userText.requestLayout();
				}
			}
		};
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(uiPrefsListener);
		userText.addDisposeListener((e) -> {
			Activator.getDefault().getPreferenceStore()
					.removePropertyChangeListener(uiPrefsListener);
		});
