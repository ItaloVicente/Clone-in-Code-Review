    /**
     * Returns a workbench label provider that is hooked up to the decorator
     * mechanism.
     *
     * @return a new <code>DecoratingLabelProvider</code> which wraps a <code>
     *   new <code>WorkbenchLabelProvider</code>
     */
    public static ILabelProvider getDecoratingWorkbenchLabelProvider() {
        return new DecoratingLabelProvider(new WorkbenchLabelProvider(),
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator());
    }
