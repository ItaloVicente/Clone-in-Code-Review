			kind = Differencer.LEFT + Differencer.DELETION;
			right = rightItem.get();
		} else if (rightIter == null) {
			kind = Differencer.LEFT + Differencer.ADDITION;
			left = leftItem.get();
		} else if (leftIter.getEntryObjectId()
				.equals(rightIter.getEntryObjectId())) {
			return null;
		} else {
			kind = Differencer.CHANGE;
			left = leftItem.get();
			right = rightItem.get();
