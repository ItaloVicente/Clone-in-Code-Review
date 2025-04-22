		String guiToolName;
		if (gui) {
			guiToolName = config.getDefaultGuiToolName();
			if (guiToolName != null) {
				return guiToolName;
			}
		}
		return config.getDefaultToolName();
