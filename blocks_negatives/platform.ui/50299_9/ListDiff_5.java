		public Object get(int index) {
			int offset = 0;
			for (int i = 0; i < subLists.length; i++) {
				int subListIndex = index - offset;
				if (subListIndex < subLists[i].size()) {
					return subLists[i].get(subListIndex);
				}
				offset += subLists[i].size();
