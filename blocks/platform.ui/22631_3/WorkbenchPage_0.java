
		if (compatibilityPart instanceof CompatibilityView) {
			legacyWindow.firePerspectiveChanged(this, getPerspective(), partReference,
					CHANGE_VIEW_SHOW);
			legacyWindow.firePerspectiveChanged(this, getPerspective(), CHANGE_VIEW_SHOW);
		}
