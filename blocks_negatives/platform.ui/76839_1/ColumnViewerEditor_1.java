				if (editorActivationListener != null
						&& !editorActivationListener.isEmpty()) {
					Object[] ls = editorActivationListener.getListeners();
					for (int i = 0; i < ls.length; i++) {
						((ColumnViewerEditorActivationListener) ls[i])
								.beforeEditorActivated(activationEvent);
