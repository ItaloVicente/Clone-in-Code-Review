	}



	public IWorkbenchPartReference getOldPartRef() {
		return oldPartRef;
	}

	public void setOldPartRef(IWorkbenchPartReference oldPartRef) {
		this.oldPartRef = oldPartRef;
	}

	protected void addActionSet(IActionSetDescriptor newDesc) {
		IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
		try {
