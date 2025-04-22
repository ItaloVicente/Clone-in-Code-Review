		if (provider instanceof ITreePathLabelProvider){
			@SuppressWarnings("unchecked")
			ITreePathLabelProvider<E> iTreePathLabelProvider = (ITreePathLabelProvider<E>) provider;
			treePathLabelProvider = iTreePathLabelProvider;
		}
		
