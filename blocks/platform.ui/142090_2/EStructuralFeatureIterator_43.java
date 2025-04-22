		int length = getLength();
		int index;
		if (!reverse) {
			index = position + startIndex;
			if (!includeStart) {
				index++;
			}
			if (index > length) {
				return false;
			}
		}
		else {
			int end = startIndex;
			if (end == -1) {
				end = length - 1;
			}
			index = end - position + 2;
			if (!includeStart) {
				index--;
			}
			if (index < 1) {
				return false;
			}
		}
		propertyNodePointer.setIndex(index - 1);
		return true;
	}
