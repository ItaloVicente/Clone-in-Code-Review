				IContributionManager parent = getParent();
				if (parent instanceof SubToolBarManager) {
					SubToolBarManager subManager = (SubToolBarManager) parent;
					IContributionItem item = subManager.getParent()
							.find(getId());
					if (item != null) {
						item.setVisible(visible);
					}
				}
