				public void menuDetected(MenuDetectEvent e) {
					popupMgr.remove(new ActionContributionItem(compareAction));
					popupMgr.remove(new ActionContributionItem(
							compareVersionsAction));
				}
			});
		}
