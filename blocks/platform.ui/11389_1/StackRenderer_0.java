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
		if (busy && !renderingUtils.addCSSClass(cti, CTABITEM_BUSY_CLASSNAME)) {
			cti.setFont(createFont(cti.getFont(), SWT.ITALIC));
		} else if (!busy
				&& !renderingUtils.removeCSSClass(cti, CTABITEM_BUSY_CLASSNAME)) {
			cti.setFont(createFont(cti.getFont(), SWT.NONE));
