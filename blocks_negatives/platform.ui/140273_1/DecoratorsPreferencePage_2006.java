    }

    /**
     * Get the decorator definitions for the workbench.
     */
    private DecoratorDefinition[] getAllDefinitions() {
        return getDecoratorManager().getAllDecoratorDefinitions();
    }

    /**
     * Get the DecoratorManager being used for this page.
     *
     * @return the decorator manager
     */
    private DecoratorManager getDecoratorManager() {
        return WorkbenchPlugin.getDefault().getDecoratorManager();
    }
