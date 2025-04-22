		if (theme != null) {
			IThemeDescriptor[] descs = WorkbenchPlugin.getDefault().getThemeRegistry().getThemes();
			for (int i = 0; i < descs.length; i++) {
				if (descs[i].getId().equals(theme.getId()) && descs[i].getDescription() != null) {
					description = descs[i].getDescription();
					break;
				}
