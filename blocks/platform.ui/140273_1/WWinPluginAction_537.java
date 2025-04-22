		removeFromActionList(this);
		if (retargetAction != null) {
			window.getPartService().removePartListener(retargetAction);
			retargetAction.dispose();
			retargetAction = null;
		}
		window.getSelectionService().removeSelectionListener(this);
		super.dispose();
