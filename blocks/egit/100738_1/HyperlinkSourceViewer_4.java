		}
		customColors.clear();
		super.handleDispose();
	}

	@Override
	public void refresh() {
		setDocument(getDocument(), getAnnotationModel());
	}

	protected void async(Runnable runnable) {
		Control control = getControl();
		if (control != null && !control.isDisposed()) {
			control.getDisplay().asyncExec(() -> {
				if (!control.isDisposed()) {
					runnable.run();
				}
			});
		}
	}

	protected void handleEditorPreferencesChange(PropertyChangeEvent event) {
		switch (event.getProperty()) {
		case SpellingService.PREFERENCE_SPELLING_ENABLED:
			boolean isEnabled = EditorsUI.getPreferenceStore()
					.getBoolean(SpellingService.PREFERENCE_SPELLING_ENABLED);
			updateSpellChecking(isEnabled);
			break;
		case AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND:
		case AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT:
		case AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND:
		case AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT:
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND:
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT:
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND:
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT:
			async(() -> setColors());
			break;
		default:
			break;
		}
	}

	protected void handleJFacePreferencesChange(PropertyChangeEvent event) {
		if (JFacePreferences.HYPERLINK_COLOR.equals(event.getProperty())) {
			async(() -> invalidateTextPresentation());
		}
	}

	protected void setFont(Font font) {
		StyledText styledText = getTextWidget();
		IVerticalRuler verticalRuler = getVerticalRuler();
		if (getDocument() != null) {
			ISelectionProvider provider = getSelectionProvider();
			ISelection selection = provider.getSelection();
			int topIndex = getTopIndex();

			Control parent = getControl();
			parent.setRedraw(false);
			styledText.setFont(font);
			if (verticalRuler instanceof IVerticalRulerExtension) {
				IVerticalRulerExtension e = (IVerticalRulerExtension) verticalRuler;
				e.setFont(font);
