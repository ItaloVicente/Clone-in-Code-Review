		super.markDirty();
		IContributionManager parent = getParent();
		if (parent != null) {
			parent.markDirty();
		}
	}

