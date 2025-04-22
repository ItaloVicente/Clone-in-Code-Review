		try {
			input = factory.createElement(inputMem);
		} catch (IllegalArgumentException exc) {
			return new NullEditorInput(exc.getMessage());
		}

