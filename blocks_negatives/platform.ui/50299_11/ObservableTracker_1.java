		Set lastObservableSet = (Set) currentGetterCalledSet.get();
		IChangeListener lastChangeListener = (IChangeListener) currentChangeListener
				.get();
		IStaleListener lastStaleListener = (IStaleListener) currentStaleListener
				.get();
		Integer lastIgnore = (Integer) currentIgnoreCount.get();
