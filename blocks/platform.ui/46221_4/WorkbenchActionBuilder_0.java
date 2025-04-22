		String menuProxy = System.getenv("UBUNTU_MENUPROXY"); //$NON-NLS-1$
		String desktopSession = System.getenv("DESKTOP_SESSION"); //$NON-NLS-1$
		String os = Platform.getOS();
		String ws = Platform.getWS();
		boolean workaroundEnabled = !"false".equals(System.getProperty("eclipse.workaround.bug461311")); //$NON-NLS-1$ //$NON-NLS-2$

		boolean radioButtonsMightCauseCrash = ((menuProxy == null) || !menuProxy.equals("0")) //$NON-NLS-1$
				&& Platform.WS_GTK.equals(ws) && Platform.OS_LINUX.equals(os)
				&& (desktopSession == null || desktopSession.equals("ubuntu")) //$NON-NLS-1$
				&& workaroundEnabled;
		if (!radioButtonsMightCauseCrash) {
			menu.add(ContributionItemFactory.OPEN_WINDOWS.create(getWindow()));
		}
