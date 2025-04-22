				SWT.MULTI | SWT.V_SCROLL | SWT.WRAP) {
			@Override
			protected void handleJFacePreferencesChange(
					PropertyChangeEvent event) {
				if (JFaceResources.TEXT_FONT.equals(event.getProperty())) {
					Font themeFont = UIUtils.getFont(
							UIPreferences.THEME_CommitMessageEditorFont);
					Font jFaceFont = JFaceResources.getTextFont();
					if (themeFont.equals(jFaceFont)) {
						setFont(jFaceFont);
					}
				} else {
					super.handleJFacePreferencesChange(event);
				}
			}
		};
