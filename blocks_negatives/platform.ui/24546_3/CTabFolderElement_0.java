	private void resetChildrenBackground(Composite composite) {
		for (Control control : composite.getChildren()) {
			resetChildBackground(control);
			if (control instanceof Composite) {
				resetChildrenBackground((Composite) control);
			}
		}
	}

	private void resetChildBackground(Control control) {
		Color backgroundSetByRenderer = (Color) control
				.getData(BACKGROUND_SET_BY_TAB_RENDERER);
		if (backgroundSetByRenderer != null) {
			if (control.getBackground() == backgroundSetByRenderer) {
				control.setBackground(null);
			}
			control.setData(BACKGROUND_SET_BY_TAB_RENDERER, null);
		}
	}

	public static void setBackgroundOverriddenDuringRenderering(
			Composite composite, Color background) {
		composite.setBackground(background);
		composite.setData(BACKGROUND_SET_BY_TAB_RENDERER, background);

		for (Control control : composite.getChildren()) {
			if (!CompositeElement.hasBackgroundOverriddenByCSS(control)) {
				control.setBackground(background);
				control.setData(BACKGROUND_SET_BY_TAB_RENDERER, background);
			}
		}
	}

