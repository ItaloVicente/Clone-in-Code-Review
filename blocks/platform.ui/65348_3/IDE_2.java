		IUnknownEditorStrategy strategy = null;
		if (allowInteractive /* TODO && strategy.isInteractive() */) {
			strategy = getUnknowEditorStrategy();
		} else {
			strategy = new SystemEditorOrTextEditorStrategy();
		}
