		int hint = ValueUtils.getCollectionHint(pd.getEType().getInstanceClass());
		if (hint == -1) {
			return false;
		}
		if (hint == 1) {
			return true;
		}
