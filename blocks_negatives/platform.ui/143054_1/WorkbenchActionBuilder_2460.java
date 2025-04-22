        }
        }
        else {
	    	if (infos == null) {
	    		infos = IDEWorkbenchPlugin.getDefault().getFeatureInfos();
	    	}
	    	boolean found = hasTipsAndTricks(infos);
	    	prefs.setValue(tipsAndTricksKey, String.valueOf(found));
	    	if (found) {
	            tipsAndTricksAction = IDEActionFactory.TIPS_AND_TRICKS
