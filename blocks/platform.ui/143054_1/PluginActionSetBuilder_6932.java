				String testId = null;
				if (item instanceof PluginActionCoolBarContributionItem) {
					testId = ((PluginActionCoolBarContributionItem) item).getActionSetId();
				}
				if (testId == null) {
					break;
				}

				if (itemId != null) {
					if (itemId.compareTo(testId) < 1) {
