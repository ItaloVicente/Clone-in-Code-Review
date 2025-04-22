	List<String> getDefaultFastViewBarViewIds() {
		List<String> fastViewIds = new ArrayList<>();
		IMemento fastViews = getChild(IWorkbenchConstants.TAG_FAST_VIEWS);
		if (fastViews != null) {
			for (IMemento view : fastViews.getChildren(IWorkbenchConstants.TAG_VIEW)) {
				fastViewIds.add(view.getString(IWorkbenchConstants.TAG_ID));
			}
		}

		return fastViewIds;
	}

