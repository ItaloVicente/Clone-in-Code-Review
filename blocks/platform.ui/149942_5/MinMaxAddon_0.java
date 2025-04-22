		MPerspective persp;
		if (win == null) {
			persp = null;
		} else {
			persp = modelService.getActivePerspective(win);
		}
