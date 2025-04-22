	public static DirtyStateTracker getDirtyStateTracker() {
		return dirtyStateTracker;
	}

	protected MPart getActivePart(IWorkbenchWindow window) {
		MPart part = null;
		if (window instanceof WorkbenchWindow) {
			EPartService partService = null;
			try {
				partService = ((WorkbenchWindow) window).getModel().getContext().get(EPartService.class);
			} catch (Exception e) {
			}
			if (partService != null) {
				part = partService.getActivePart();
			}
		}
		return part;
	}
