
		if (MenuHelper.getMode(commandAddition) == CommandContributionItem.MODE_FORCE_TEXT) {
			item.getTags().add("FORCE_TEXT"); //$NON-NLS-1$
			item.setLabel(MenuHelper.getLabel(commandAddition));
		}

