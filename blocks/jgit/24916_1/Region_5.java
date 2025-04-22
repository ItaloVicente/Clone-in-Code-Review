	static Region merge(Region a
		Region resultRegion;
		Region otherRegion;

		if (a.resultStart < b.resultStart) {
			resultRegion = a.deepCopy();
			otherRegion = b.deepCopy();
		} else {
			resultRegion = b.deepCopy();
			otherRegion = a.deepCopy();
		}

		final Region result = resultRegion;
		while (otherRegion != null) {
			if (resultRegion.next != null
					&& resultRegion.resultStart < otherRegion.resultStart
					&& resultRegion.next.resultStart < otherRegion.resultStart) {
				resultRegion = resultRegion.next;
			} else {
				Region otherNext = otherRegion.next;

				otherRegion.next = resultRegion.next;
				resultRegion.next = otherRegion;
				resultRegion = otherRegion;

				otherRegion = otherNext;
			}
		}
		return result;
	}

