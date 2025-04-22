		MergeStrategy target = null;
		if (mergeStrategy != null) {
			target = MergeStrategy.get(mergeStrategy);
		}
		this.mergeStrategy = target != null ? target : Activator.getDefault()
				.getPreferredMergeStrategy();
