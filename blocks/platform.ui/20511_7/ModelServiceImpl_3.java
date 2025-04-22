

		MUIElement storedPerspState = cloneSnippet(appContext.get(MApplication.class),
				persp.getElementId(), window);
		if (storedPerspState instanceof MPerspective) {
			MPerspective state = (MPerspective) storedPerspState;
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
