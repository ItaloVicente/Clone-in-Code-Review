					renderer.clearModelToContribution(opaqueMenuItem, (IContributionItem) item);
					opaqueMenuItem.setOpaqueItem(null);
				}
			} else if (menuElement instanceof MOpaqueMenuSeparator) {
				MOpaqueMenuSeparator opaqueMenuItem = (MOpaqueMenuSeparator) menuElement;
				Object item = opaqueMenuItem.getOpaqueItem();
				if (item instanceof IContributionItem) {
					renderer.clearModelToContribution(opaqueMenuItem, (IContributionItem) item);
					opaqueMenuItem.setOpaqueItem(null);
