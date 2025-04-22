				}
			}
		}
		return null;
	}

	private void markLocationForTab(IEditorPart part) {
		if (part instanceof ErrorEditorPart) {
			updateActions();
			return;
		}
