    public boolean editActionSets() {
		Perspective persp = getActivePerspective();
		if (persp == null) {
			return false;
		}

		CustomizePerspectiveDialog dlg = legacyWindow.createCustomizePerspectiveDialog(persp,
				window.getContext());
		boolean ret = (dlg.open() == Window.OK);
		if (ret) {
			legacyWindow.updateActionSets();
			legacyWindow.firePerspectiveChanged(this, getPerspective(), CHANGE_RESET);
			legacyWindow.firePerspectiveChanged(this, getPerspective(), CHANGE_RESET_COMPLETE);
		}
		return ret;
    }


