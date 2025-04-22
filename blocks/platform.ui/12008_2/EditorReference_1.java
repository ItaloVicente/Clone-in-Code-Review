			} else if (getEditorInput() instanceof NullEditorInput) {
				String errorMessage = ((NullEditorInput) getEditorInput()).getErrorMessage();
				if (errorMessage != null) {
					return createErrorPart(NLS.bind(
							WorkbenchMessages.EditorManager_unableToCreateEditor, errorMessage));
				}
