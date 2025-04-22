	@Override
	public void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle);
	}

	public static int getDefaultShellStyle() {
		return SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.ON_TOP | getShellModality() | SWT.RESIZE
				| getDefaultOrientation();
	}

	private static int getShellModality() {
		return System.getProperty("nonmodal", "notset").equals("notset") ? SWT.APPLICATION_MODAL : 0; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

