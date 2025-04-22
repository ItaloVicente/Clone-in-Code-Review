			subListIndex -= firstSublist.size();
			if (subListIndex < middleSublist.size()) {
				return middleSublist.get(subListIndex);
			}
			subListIndex -= middleSublist.size();
			if (subListIndex < lastSublist.size()) {
				return lastSublist.get(subListIndex);
			}

