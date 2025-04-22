	@Override
	public void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle);
	}

	public int getDefaultShellStyle() {
		return SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.ON_TOP | SWT.RESIZE | getShellModality()
				| getDefaultOrientation();
	}

	private int getShellModality() {
		return fNonModal ? 0 : SWT.APPLICATION_MODAL;
	}

	public WizardDialog setNonModal(boolean nonModal) {
		fNonModal = nonModal;
		setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL & getShellModality());
		return this;
	}

	public boolean isNonModal() {
		return fNonModal == true;
	}

