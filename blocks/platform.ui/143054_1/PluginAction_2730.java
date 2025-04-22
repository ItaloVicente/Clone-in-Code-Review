		selectionChanged(StructuredSelection.EMPTY);
	}

	protected final void createDelegate() {
		if (delegate == null && runAttribute != null) {
			try {
				Object obj = WorkbenchPlugin.createExtension(configElement, runAttribute);
				delegate = validateDelegate(obj);
				initDelegate();
				refreshEnablement();
			} catch (Throwable e) {
				runAttribute = null;
				IStatus status = null;
				if (e instanceof CoreException) {
					status = ((CoreException) e).getStatus();
				} else {
					status = StatusUtil.newStatus(IStatus.ERROR, "Internal plug-in action delegate error on creation.", //$NON-NLS-1$
							e);
				}
				String id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
				WorkbenchPlugin.log("Could not create action delegate for id: " + id, status); //$NON-NLS-1$
				return;
			}
		}
	}

	protected IActionDelegate validateDelegate(Object obj) throws WorkbenchException {
		if (obj instanceof IActionDelegate) {
