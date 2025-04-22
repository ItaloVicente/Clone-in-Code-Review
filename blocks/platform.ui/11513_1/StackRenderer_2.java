		} else if (UIEvents.UILabel.BUSY.equals(attName)) {
			updateBusyIndicator(cti, part, (Boolean) newValue);
		}
	}

	@SuppressWarnings("restriction")
	protected void updateBusyIndicator(CTabItem cti, MPart part, boolean busy) {
		IEclipseContext context = part.getContext();
		CSSRenderingUtils renderingUtils = context.get(CSSRenderingUtils.class);
		if (renderingUtils == null) {
			return;
		}
		if (busy) {
			renderingUtils.addCSSClass(cti, CSSConstants.CSS_BUSY_CLASS);
		} else {
			renderingUtils.removeCSSClass(cti, CSSConstants.CSS_BUSY_CLASS);
