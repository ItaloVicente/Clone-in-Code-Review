	}

	private static ToolItem getStopButton(ProgressMonitorPart part) {
		for (Control control : part.getChildren()) {
			if (control instanceof ToolBar) {
				for (ToolItem item : ((ToolBar) control).getItems()) {
					if (item.getToolTipText().equals(JFaceResources.getString("ProgressMonitorPart.cancelToolTip"))) { //$NON-NLS-1$ ))
						return item;
					}
				}
			}
		}
		return null;
	}

	private void stopAndDisconnectCurrentWork() {
		if (refreshProposalsJob != null) {
			refreshProposalsJob.cancel();
		}
	}

	private void proposalsUpdated() {
		tree.setInput(potentialProjects);
		tree.setCheckedElements(this.notAlreadyExistingProjects.toArray());
