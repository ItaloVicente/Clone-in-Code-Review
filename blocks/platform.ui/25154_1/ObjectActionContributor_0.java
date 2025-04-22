
		@Override
		protected void insertAfter(IContributionManager mgr, String refId, IContributionItem item) {
			if (item.getId() == null || mgr.find(item.getId()) == null) {
				mgr.insertAfter(refId, item);
			}
		}
