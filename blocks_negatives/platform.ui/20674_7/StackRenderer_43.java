				if (menuElement instanceof MOpaqueMenuItem) {
					IContributionItem item = (IContributionItem) ((MOpaqueMenuItem) menuElement)
							.getOpaqueItem();
					if (item != null && item.isVisible()) {
						return true;
					}
				} else if (menuElement instanceof MOpaqueMenuSeparator) {
					IContributionItem item = (IContributionItem) ((MOpaqueMenuSeparator) menuElement)
							.getOpaqueItem();
