
	private void setPerspectiveChangeHelper(MPerspective oldPersp) {
		if (oldPersp == null) {
			return;
		}
		final IEclipseContext context = oldPersp.getContext();
		final MPart activePart = context.get(EPartService.class).getActivePart();

		ContextFunction helper = new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (activePart == null) {
					return null;
				}
				boolean isView = activePart.getTags().contains("View"); //$NON-NLS-1$
				if (isView) {
					return null;
				}
				return activePart;
			}
		};
		oldPersp.getTransientData().put(PartServiceImpl.PERSPECTIVE_CHANGE_HELPER, helper);
	}

	private void unsetPerspectiveChangeHelper(MPerspective oldPersp) {
		if (oldPersp != null) {
			oldPersp.getTransientData().remove(PartServiceImpl.PERSPECTIVE_CHANGE_HELPER);
		}
	}
