
	public static boolean isUsable(final Viewer viewer) {
		return viewer != null && isUsable(viewer.getControl());
	}

	public static boolean isUsable(final Control control) {
		return control != null && !control.isDisposed();
	}
