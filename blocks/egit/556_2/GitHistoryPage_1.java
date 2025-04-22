			});
		} else {
			c.addMenuDetectListener(new MenuDetectListener() {

				public void menuDetected(MenuDetectEvent e) {
					popupMgr.remove(new ActionContributionItem(compareAction));
					popupMgr.remove(new ActionContributionItem(
							compareVersionsAction));
					popupMgr.remove(new ActionContributionItem(
							viewVersionsAction));
				}
			});
		}
