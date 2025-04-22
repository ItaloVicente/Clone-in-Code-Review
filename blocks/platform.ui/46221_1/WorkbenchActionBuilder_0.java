		String menuProxy = System.getenv("UBUNTU_MENUPROXY"); //$NON-NLS-1$

		boolean radioButtonsMightCauseCrash = ((menuProxy == null) || !menuProxy.equals("0")) //$NON-NLS-1$
				&& SWT.getPlatform().equals("gtk"); //$NON-NLS-1$

		if (!radioButtonsMightCauseCrash) {
