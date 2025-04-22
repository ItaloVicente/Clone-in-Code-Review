	private String getLocationFromCommandLine() {
		final String fullArgName = "-" + E4Workbench.SHOW_LOCATION;
		for (int i = 0; i < args.length; i++) {
			if (fullArgName.equalsIgnoreCase(args[i])) { //$NON-NLS-1$
				String name = null;
				if (args.length > i + 1) {
					name = args[i + 1];
				}
				if (name != null && name.indexOf("-") == -1) { //$NON-NLS-1$
					return name;
				}
				return Platform.getLocation().toOSString();
			}
		}
		return null;
	}

