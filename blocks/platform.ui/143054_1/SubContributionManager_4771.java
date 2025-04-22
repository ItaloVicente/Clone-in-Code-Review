		IContributionItem[] result = new IContributionItem[mapItemToWrapper
				.size()];
		mapItemToWrapper.keySet().toArray(result);
		return result;
	}

	public IContributionManager getParent() {
		return parentMgr;
	}

	@Override
