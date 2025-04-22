			Object oldAction = configuration.getProperty(P_OPEN_ACTION);
			if (!(oldAction instanceof Action))
				return;

			final GitOpenInCompareAction openInCompareAction = new GitOpenInCompareAction(
					configuration, (Action) oldAction);
			configuration.setProperty(P_OPEN_ACTION, openInCompareAction);
