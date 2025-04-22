				for (Iterator<MToolBarElement> it = toolbar.getChildren().iterator(); it.hasNext();) {
					MToolBarElement element = it.next();
					if (OpaqueElementUtil.isOpaqueToolItem(element)) {
						IContributionItem item = tbmr.getContribution(element);
						if (item != null) {
							tbmr.clearModelToContribution(element, item);
						}
						OpaqueElementUtil.clearOpaqueItem(element);
						it.remove();
					}
				}
