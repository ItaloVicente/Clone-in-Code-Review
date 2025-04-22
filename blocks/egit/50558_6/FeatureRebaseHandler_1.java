
		if (STOPPED.equals(status)) {
			try {
				showInteractiveRebaseView(event);
			} catch (PartInitException e) {
				return error(e.getMessage(), e);
			}
