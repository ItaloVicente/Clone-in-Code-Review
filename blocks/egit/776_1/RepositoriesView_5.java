	public void setReactOnSelection(boolean reactOnSelection) {
		this.reactOnSelection = reactOnSelection;
		if (this.reactOnSelection) {
			ISelectionService srv = (ISelectionService) getSite().getService(
					ISelectionService.class);
			reactOnSelection(srv.getSelection());
		}
	}
