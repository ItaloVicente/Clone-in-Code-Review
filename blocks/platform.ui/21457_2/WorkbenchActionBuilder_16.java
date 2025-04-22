		} else if (sameState && "false".equals(showQuickStart)) { //$NON-NLS-1$
		} else {
			infos = IDEWorkbenchPlugin.getDefault().getFeatureInfos();
			boolean found = hasWelcomePage(infos);
			prefs.setValue(quickStartKey, String.valueOf(found));
			if (found) {
				quickStartAction = IDEActionFactory.QUICK_START.create(window);
				register(quickStartAction);
			}
		}

		String tipsAndTricksKey = IDEActionFactory.TIPS_AND_TRICKS.getId();
		String showTipsAndTricks = prefs.getString(tipsAndTricksKey);
		if (sameState && "true".equals(showTipsAndTricks)) { //$NON-NLS-1$
			tipsAndTricksAction = IDEActionFactory.TIPS_AND_TRICKS
