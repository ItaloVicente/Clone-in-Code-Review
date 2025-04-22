

		EPerspectiveRestoreService restoreService = appContext
				.get(EPerspectiveRestoreService.class);
		if (restoreService == null)
			return;

		MPerspective state = restoreService.reloadPerspective(persp.getElementId(), window);
		if (state != null) {
			boolean wasPerspectiveActive = (persp.getParent().getSelectedElement() == persp);
			persp.setToBeRendered(false);

			EcoreUtil.replace((EObject) persp, (EObject) state);

			if (wasPerspectiveActive) { // switch to perspective only if it was active
				IEclipseContext context = window.getContext();
				if (context == null) {
					context = appContext;
				}
				EPartService ps = context.get(EPartService.class);
				ps.switchPerspective(state); // no null-check, because we want to fail early
			}
		}
