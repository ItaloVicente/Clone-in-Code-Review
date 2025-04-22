		if (mpart == null) {
			return false;
		}
		MPerspective perspective = getCurrentPerspective();
		MPerspective persp = modelService.getPerspectiveFor(mpart);
		if (persp != perspective) {
			return false;
		}
		return partService.isPartVisible(mpart);
