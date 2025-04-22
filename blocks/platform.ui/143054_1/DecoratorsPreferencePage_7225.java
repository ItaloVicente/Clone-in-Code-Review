	}

	private DecoratorDefinition[] getAllDefinitions() {
		return getDecoratorManager().getAllDecoratorDefinitions();
	}

	private DecoratorManager getDecoratorManager() {
		return WorkbenchPlugin.getDefault().getDecoratorManager();
	}
