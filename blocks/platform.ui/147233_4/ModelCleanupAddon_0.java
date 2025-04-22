
		cleanHiddenCompatibilityEditors();
	}

	private void cleanHiddenCompatibilityEditors() {
		List<MPart> compatEditors = modelService.findElements(application, CompatibilityEditor.MODEL_ELEMENT_ID,
				MPart.class);
		for (MPart editor : compatEditors) {
			if (!editor.isToBeRendered()) {
				editor.getParent().getChildren().remove(editor);
			}
		}
