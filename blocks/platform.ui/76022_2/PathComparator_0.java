		if (arg0.equals(arg1)) {
			return 0;
		}
		if (arg0.getDevice() != null && arg1.getDevice() == null) {
			return +1;
		}
		if (arg0.getDevice() == null && arg1.getDevice() != null) {
			return -1;
		}
		if (arg0.getDevice() != null && arg1.getDevice() != null) {
			int compare = arg0.getDevice().compareTo(arg1.getDevice());
			if (compare != 0) {
				return compare;
			}
		}
		if (arg0.isAbsolute() && !arg1.isAbsolute()) {
			return +1;
		}
		if (!arg0.isAbsolute() && arg1.isAbsolute()) {
			return -1;
		}
		if (arg0.isUNC() && !arg1.isUNC()) {
			return +1;
		}
		if (!arg0.isUNC() && arg1.isUNC()) {
			return -1;
		}
		for (int i = 0; i < Math.min(arg0.segmentCount(), arg1.segmentCount()); i++) {
			int res = arg0.segment(i).compareTo(arg1.segment(i));
			if (res != 0) {
				return res;
			}
		}
		return arg0.segmentCount() - arg1.segmentCount();
