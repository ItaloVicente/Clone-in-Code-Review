		final TextCellEditor editor = new TextCellEditor(tree);
		editor.setValidator(new ICellEditorValidator() {

			public String isValid(Object value) {
				String editedValue = value.toString();
				return editedValue.length() > 0 ? null
						: UIText.ConfigurationEditorComponent_EmptyStringNotAllowed;
			}
		});
		editor.addListener(new ICellEditorListener() {

			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				setErrorMessage(editor.getErrorMessage());
			}

			public void cancelEditor() {
				setErrorMessage(null);
			}

			public void applyEditorValue() {
				setErrorMessage(null);
			}
		});

