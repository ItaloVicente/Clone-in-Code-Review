		editor.setValidator(value -> {
			String currentText = value.toString().trim();
			if (currentText.isEmpty()) {
				return UIText.GitHistoryPage_filterRefDialog_refEmptyError;
			}
			return null;
		});
		editor.addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				if (newValidState) {
					message.setImage(null);
					message.setText(
							UIText.GitHistoryPage_filterRefDialog_dialogMessage);
				} else {
					message.setImage(PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
					message.setText(editor.getErrorMessage());
				}
				getButton(OK).setEnabled(newValidState);
			}

			@Override
			public void cancelEditor() {
				editingMode = false;
				editorValueChanged(false, true);
				updateButtonEnablement();
			}

			@Override
			public void applyEditorValue() {
				cancelEditor();
			}
		});
