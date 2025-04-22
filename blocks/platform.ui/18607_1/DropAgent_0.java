	public void reactivatePart(MUIElement dragElement) {
		IEclipseContext context = dndManager.getModelService().getContainingContext(dragElement);
		if (context == null)
			return;

		EPartService ps = context.get(EPartService.class);
		if (ps == null)
			return;

		MPart partToActivate = null;
		if (dragElement instanceof MPart) {
			partToActivate = (MPart) dragElement;
		} else if (dragElement instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) dragElement;
			if (ph.getRef() instanceof MPart) {
				partToActivate = (MPart) ph.getRef();
			}
		} else if (dragElement instanceof MPartStack) {
			MPartStack stack = (MPartStack) dragElement;
			if (stack.getSelectedElement() instanceof MPart) {
				partToActivate = (MPart) stack.getSelectedElement();
			} else if (stack.getSelectedElement() instanceof MPlaceholder) {
				MPlaceholder ph = (MPlaceholder) stack.getSelectedElement();
				if (ph.getRef() instanceof MPart) {
					partToActivate = (MPart) ph.getRef();
				}
			}
		}

		if (partToActivate != null) {
			ps.activate(null);
			ps.activate(partToActivate);
		}
	}

