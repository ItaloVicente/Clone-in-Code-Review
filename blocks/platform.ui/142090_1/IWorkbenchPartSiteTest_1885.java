		assertFalse(service.isActive());

	}


	abstract protected IWorkbenchPart createTestPart(IWorkbenchPage page)
			throws Throwable;

	abstract protected String getTestPartId() throws Throwable;

	abstract protected String getTestPartName() throws Throwable;

	protected String getTestPartPluginId() throws Throwable {
		return "org.eclipse.ui.tests";
	}
