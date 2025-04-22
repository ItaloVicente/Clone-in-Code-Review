		return dropSide == SWT.TOP || dropSide == SWT.BOTTOM;
	}


	/**
	 * Answer true if the side is a horizonal one
	 *
	 * @param dropSide
	 * @return <code>true</code> if the side is horizontal
	 */
	private boolean isVertical(int dropSide) {
		if (forceHorizontal)
			return false;
		return dropSide == SWT.LEFT || dropSide == SWT.RIGHT;
	}

	/**
	 * Recreate the receiver given the new side
	 */
	private void recreate() {
		if (region != null && !region.isDisposed()) {
			Composite parent = region.getParent();
			boolean animating = animationItem.animationRunning();
	        AnimationManager.getInstance().removeItem(animationItem);
			region.dispose();
			createContents(parent, workbenchWindow);
			if (animating)
				animationItem.animationStart();
		}
	}

	@Override
	public String getId() {
	}

	@Override
	public String getDisplayName() {
		return WorkbenchMessages.TrimCommon_Progress_TrimName;
	}

	@Override
	public int getValidSides() {
		return SWT.BOTTOM | SWT.TOP | SWT.LEFT | SWT.RIGHT ;
	}

	@Override
	public boolean isCloseable() {
		return false;
	}

	@Override
	public void handleClose() {
	}

	@Override
	public int getWidthHint() {
		return fWidthHint;
	}

	/**
	 * Update the width hint for this control.
	 * @param w pixels, or SWT.DEFAULT
	 */
	public void setWidthHint(int w) {
		fWidthHint = w;
	}

	@Override
	public int getHeightHint() {
		return fHeightHint;
	}

	/**
	 * Update the height hint for this control.
	 * @param h pixels, or SWT.DEFAULT
	 */
	public void setHeightHint(int h) {
		fHeightHint = h;
	}

	@Override
	public boolean isResizeable() {
		return false;
