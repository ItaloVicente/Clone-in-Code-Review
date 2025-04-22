		if (Platform.OS_SOLARIS.equals(Platform.getOS())) {
			if (!Platform.WS_GTK.equals(Platform.getWS())) {
				return false;
			}
			String osVersion = WebBrowserUIPlugin.getInstance().getBundle().getBundleContext().getProperty(Constants.FRAMEWORK_OS_VERSION);
			int compareVal = new Version(osVersion).compareTo(new Version(5,10,0));
			if (compareVal < 0) {
				return false;
			}
		}
