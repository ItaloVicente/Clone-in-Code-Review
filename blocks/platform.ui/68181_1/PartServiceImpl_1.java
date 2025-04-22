		if (activePart != null) {
			MPerspective persp = modelService.getPerspectiveFor(activePart);
			if (persp != null && persp.getTransientData().containsKey(PERSP_DESELECTING)) {
				return null;
			}
		}
