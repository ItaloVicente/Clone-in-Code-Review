		HideDotFiles hideDotFiles = getConfig().getEnum(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HIDEDOTFILES
				HideDotFiles.DOTGITONLY);
		if (hideDotFiles != HideDotFiles.FALSE)
			getFS().setHidden(getDirectory()
