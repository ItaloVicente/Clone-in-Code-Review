		public E get(int index) {
			int subListIndex = index;
			if (subListIndex < firstSublist.size()) {
				return firstSublist.get(subListIndex);
			}
			subListIndex -= firstSublist.size();
			if (subListIndex < middleSublist.size()) {
				return middleSublist.get(subListIndex);
