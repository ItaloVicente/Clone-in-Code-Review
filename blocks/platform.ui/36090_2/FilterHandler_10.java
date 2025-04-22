		int low = 0;
		int high = filterFrames.length;
		while (low < high) {
			int mid = (low + high) >>> 1;
			StackFrame filter = filterFrames[mid];
			int c = methodName.compareTo(filter.methodName);
			if (c == 0) {
				c = className.compareTo(filter.className);
			}
			if (c == 0) {
				return true;
			} else if (c < 0) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
