	@Override
	public Image getToolTipImage(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipImage(object);
		}
		return super.getToolTipImage(object);
	}

	@Override
	public String getToolTipText(Object element) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipText(element);
		}
		return super.getToolTipText(element);
	}

	@Override
	public Color getToolTipBackgroundColor(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipBackgroundColor(object);
		}
		return super.getToolTipBackgroundColor(object);
	}

	@Override
	public Color getToolTipForegroundColor(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipForegroundColor(object);
		}
		return super.getToolTipForegroundColor(object);
	}

	@Override
	public Font getToolTipFont(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipFont(object);
		}
		return super.getToolTipFont(object);
	}

	@Override
	public Point getToolTipShift(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipShift(object);
		}
		return super.getToolTipShift(object);
	}

	@Override
	public boolean useNativeToolTip(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).useNativeToolTip(object);
		}
		return super.useNativeToolTip(object);
	}

	@Override
	public int getToolTipTimeDisplayed(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipTimeDisplayed(object);
		}
		return super.getToolTipTimeDisplayed(object);
	}

	@Override
	public int getToolTipDisplayDelayTime(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipDisplayDelayTime(object);
		}
		return super.getToolTipDisplayDelayTime(object);
	}

	@Override
	public int getToolTipStyle(Object object) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipStyle(object);
		}
		return super.getToolTipStyle(object);
	}

