		TextViewerAction copyAction = createFromActionFactory(
				ActionFactory.COPY, ITextOperationTarget.COPY);
		TextViewerAction selectAllAction = createFromActionFactory(
				ActionFactory.SELECT_ALL, ITextOperationTarget.SELECT_ALL);

		final TextEditorPropertyAction showWhitespaceAction = new TextEditorPropertyAction(
				UIText.SpellcheckableMessageArea_showWhitespace,
				sourceViewer,
				AbstractTextEditor.PREFERENCE_SHOW_WHITESPACE_CHARACTERS) {

			private IPainter whitespaceCharPainter;

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

			@Override
			protected void toggleState(boolean checked) {
				if (checked)
					installPainter();
				else
					uninstallPainter();
			}

			/**
			 * Installs the painter on the viewer.
			 */
			private void installPainter() {
				Assert.isTrue(whitespaceCharPainter == null);
				ITextViewer v = getTextViewer();
				if (v instanceof ITextViewerExtension2) {
					IPreferenceStore store = getStore();
					whitespaceCharPainter = new WhitespaceCharacterPainter(
							v,
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_CARRIAGE_RETURN),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LINE_FEED),
							store.getInt(AbstractTextEditor.PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE));
					((ITextViewerExtension2) v).addPainter(whitespaceCharPainter);
				}
			}

			/**
			 * Remove the painter from the viewer.
			 */
			private void uninstallPainter() {
				if (whitespaceCharPainter == null)
					return;
				ITextViewer v = getTextViewer();
				if (v instanceof ITextViewerExtension2)
					((ITextViewerExtension2) v)
							.removePainter(whitespaceCharPainter);
				whitespaceCharPainter.deactivate(true);
				whitespaceCharPainter = null;
			}
		};
