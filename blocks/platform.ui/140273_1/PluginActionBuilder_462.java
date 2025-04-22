		}

		protected void insertAfter(IContributionManager mgr, String refId, IContributionItem item) {
			mgr.insertAfter(refId, item);
		}

		protected void addGroup(IContributionManager mgr, String name) {
			mgr.add(new Separator(name));
		}
