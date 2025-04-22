		/*
		 * Remove the second QuickAccess control if an older workspace is
		 * opened.
		 * 
		 * An older workspace will create an ApplicationModel which already
		 * contains the QuickAccess elements, from the old
		 * "popuolateTopTrimContribution()" method. The new implementation of
		 * this method doesn't add the QuickAccess elements anymore but an old
		 * workbench.xmi still has these entries in it and so they need to be
		 * removed.
		 */
		cleanLegacyQuickAccessContribution();
