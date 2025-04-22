			final String tag;
			if ("popup".equals(location.getScheme())) { //$NON-NLS-1$
				mMenu.getTags().add(ContributionsAnalyzer.MC_POPUP);
				tag = "popup:" + location.getPath(); //$NON-NLS-1$
			} else {
				mMenu.getTags().add(ContributionsAnalyzer.MC_MENU);
				tag = "menu:" + location.getPath(); //$NON-NLS-1$
			}
			mMenu.getTags().add(tag);
