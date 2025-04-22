		String menuProxy = System.getenv("UBUNTU_MENUPROXY"); //$NON-NLS-1$
		String desktopSession = System.getenv("DESKTOP_SESSION"); //$NON-NLS-1$
		String os = Platform.getOS();

		boolean radioButtonsMightCauseCrash = ((menuProxy == null) || !menuProxy.equals("0")) //$NON-NLS-1$
				&& SWT.getPlatform().equals("gtk") //$NON-NLS-1$
				&& os.equals(Platform.OS_LINUX) && (desktopSession == null || desktopSession.equals("ubuntu")); //$NON-NLS-1$
		if (!radioButtonsMightCauseCrash) {
			menu.add(ContributionItemFactory.OPEN_WINDOWS.create(getWindow()));
		}
