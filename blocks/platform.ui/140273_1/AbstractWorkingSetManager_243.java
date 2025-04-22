	protected IWorkingSet restoreWorkingSet(final IMemento memento) {
		String factoryID = memento.getString(IWorkbenchConstants.TAG_FACTORY_ID);

		if (factoryID == null) {
			factoryID = AbstractWorkingSet.FACTORY_ID;
		}
		final IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(factoryID);
		if (factory == null) {
			WorkbenchPlugin.log("Unable to restore working set - cannot instantiate factory: " + factoryID); //$NON-NLS-1$
			return null;
		}
