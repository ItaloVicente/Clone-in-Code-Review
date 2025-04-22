					if (toolBarMgr != null) {
						if (bars instanceof ActionSetActionBars) {
							contributeCoolbarAction(ad, (ActionSetActionBars) bars);
						} else {
							contributeToolbarAction(ad, toolBarMgr, toolAppendIfMissing);
						}
					}
				}
			}
		}

		protected void contributeAdjunctCoolbarAction(ActionDescriptor ad, ActionSetActionBars bars) {
			String toolBarId = ad.getToolbarId();
			String toolGroupId = ad.getToolbarGroupId();

			String contributingId = bars.getActionSetId();
			ICoolBarManager coolBarMgr = bars.getCoolBarManager();
			if (coolBarMgr == null) {
