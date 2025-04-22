	@Override
	public void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle);
	}

	@Override
	public int getShellStyle() {
		return super.getShellStyle();
	}

	private static int getShellModality(boolean modal) {
		return modal ? SWT.APPLICATION_MODAL : SWT.NONE;
	}

	public WizardDialog setModal(boolean modal) {
		setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | getShellModality(modal));
		return this;
	}

	public boolean isModal() {
		return (getShellStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL;
	}

