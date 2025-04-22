		if (org.eclipse.jface.util.Util.isMac()) {
			String formattedName = (String) CARBON_KEY_LOOK_UP.get(name);
			if (formattedName != null) {
				return formattedName;
			}
		}
