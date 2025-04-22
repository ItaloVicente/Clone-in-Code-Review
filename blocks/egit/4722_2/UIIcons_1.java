	public static Image getStatusImage(MergeStatus status) {
		switch (status) {
		case ALREADY_UP_TO_DATE:
		case FAST_FORWARD:
			return null;
		case MERGED:
			return getInfoImage();
		case CONFLICTING:
			return getWarningImage();
		case FAILED:
		case NOT_SUPPORTED:
			return getErrorImage();
		}

		return null;
	}

	public static Image getStatusImage(RebaseResult.Status status) {
		switch (status) {
		case OK:
		case UP_TO_DATE:
		case ABORTED:
		case FAST_FORWARD:
			return null;
		case STOPPED:
			return getWarningImage();
		case FAILED:
			return getErrorImage();
		}

		return null;
	}

	public static Image getErrorImage() {
		return getSWTImage(SWT.ICON_ERROR);
	}

	public static Image getWarningImage() {
		return getSWTImage(SWT.ICON_WARNING);
	}

	public static Image getInfoImage() {
		return getSWTImage(SWT.ICON_INFORMATION);
	}

	private static Image getSWTImage(int imageID) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display == null || display.isDisposed())
			return null;
		return display.getSystemImage(imageID);
	}

