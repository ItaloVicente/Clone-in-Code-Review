	protected List<?> getPerspectiveExtensionActionSets() {
		if (descriptor == null) {
			return Collections.emptyList();
		}
		return page.getPerspectiveExtensionActionSets(descriptor.getOriginalId());
	}

	public void turnOnActionSets(IActionSetDescriptor[] newArray) {
		for (int i = 0; i < newArray.length; i++) {
			IActionSetDescriptor descriptor = newArray[i];
			addActionSet(descriptor);
		}
	}

	public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
		for (int i = 0; i < toDisable.length; i++) {
			IActionSetDescriptor descriptor = toDisable[i];
			turnOffActionSet(descriptor);
		}
	}

	public void turnOffActionSet(IActionSetDescriptor toDisable) {
		removeActionSet(toDisable);
	}

	protected void addActionSet(IActionSetDescriptor newDesc) {
		IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
		try {
