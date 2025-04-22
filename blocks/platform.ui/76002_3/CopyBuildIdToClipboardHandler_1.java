		Properties sp = System.getProperties();
		String osInfo = String.format("OS: %s, v.%s, %s / %s", //$NON-NLS-1$
				sp.get(OS_NAME), sp.get(OS_VERSION), sp.get(OSGI_ARCH), sp.get(OSGI_WS));

		String gtkVer = sp.getProperty(SWT_GTK_VERSION);
		if (gtkVer != null) {
			osInfo += String.format(" %s", gtkVer); //$NON-NLS-1$
		}
		String webkitGtkVer = sp.getProperty(SWT_WEBKITGTK_VERSION);
		if (webkitGtkVer != null) {
			osInfo += String.format(", WebKit %s", webkitGtkVer); //$NON-NLS-1$
		}

		String toCopy = String.format("%s%n%s%n%s%n%s%n", lines[0], lines[2], lines[3], osInfo); //$NON-NLS-1$
