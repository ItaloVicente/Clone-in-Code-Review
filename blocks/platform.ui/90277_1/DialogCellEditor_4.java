        button.addSelectionListener(widgetSelectedAdapter(event -> {
			button.removeFocusListener(getButtonFocusListener());

			Object newValue = openDialogBox(editor);

			button.addFocusListener(getButtonFocusListener());

			if (newValue != null) {
		        boolean newValidState = isCorrect(newValue);
		        if (newValidState) {
		            markDirty();
		            doSetValue(newValue);
		        } else {
		            setErrorMessage(MessageFormat.format(getErrorMessage(), newValue.toString()));
		        }
		        fireApplyEditorValue();
		    }
		}));
