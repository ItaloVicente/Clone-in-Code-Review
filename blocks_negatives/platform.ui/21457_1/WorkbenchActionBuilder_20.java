        }
        }
        else {
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
            tipsAndTricksAction = IDEActionFactory.TIPS_AND_TRICKS
