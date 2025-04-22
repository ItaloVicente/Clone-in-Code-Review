	MPart activePart = null;

	@Inject
	void trackActivePart(@Optional @Named(IServiceConstants.ACTIVE_PART) MPart p) {
		if (activePart == p) {
			return;
		}

		if (activePart != null) {
			activePart.getTags().remove(CSSConstants.CSS_ACTIVE_CLASS);

			MUIElement parent = activePart.getParent();
			if (parent == null && activePart.getCurSharedRef() != null) {
				MPlaceholder ph = activePart.getCurSharedRef();
				parent = ph.getParent();
			}
			if (parent instanceof MPartStack) {
				styleStack((MPartStack) parent, false);
			} else {
				if (activePart.getWidget() != null)
					setCSSInfo(activePart, activePart.getWidget());
			}
		}

		activePart = p;

		if (activePart != null) {
			activePart.getTags().add(CSSConstants.CSS_ACTIVE_CLASS);
			MUIElement parent = activePart.getParent();
			if (parent == null && activePart.getCurSharedRef() != null) {
				MPlaceholder ph = activePart.getCurSharedRef();
				parent = ph.getParent();
			}
			if (parent instanceof MPartStack && parent.getWidget() != null) {
				styleStack((MPartStack) parent, true);
			} else if (activePart.getWidget() != null) {
				setCSSInfo(activePart, activePart.getWidget());
			}
		}
	}

	private void styleStack(MPartStack stack, boolean active) {
		if (!active)
			stack.getTags().remove(CSSConstants.CSS_ACTIVE_CLASS);
		else
			stack.getTags().add(CSSConstants.CSS_ACTIVE_CLASS);

		if (stack.getWidget() != null)
			setCSSInfo(stack, stack.getWidget());
	}

