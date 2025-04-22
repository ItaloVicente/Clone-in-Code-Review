	private class TargetsListDiffAdvisor<E extends IObservable> extends ListDiffVisitor<E> {
	
		@Override
		public void handleAdd(int index, E element) {
			targetAdded(element);
		}
	
		@Override
		public void handleRemove(int index, E element) {
			targetRemoved(element);
		}
	
	}

