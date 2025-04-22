		if (arg0.equals(arg1)) {
			return 0;
		}
		int res = 0;
		if (arg0.getDevice() != null && arg1.getDevice() == null) {
			return +1;
		}
		if (arg0.getDevice() == null && arg1.getDevice() != null) {
			return -1;
		}
		if (arg0.getDevice() != null && arg1.getDevice() != null) {
			res = arg0.getDevice().compareTo(arg1.getDevice());
			if (res != 0) {
				return res;
			}
		}
		res = Boolean.compare(arg0.isAbsolute(), arg1.isAbsolute());
		if (res != 0) {
			return res;
		}
		res = Boolean.compare(arg0.isUNC(), arg1.isUNC());
		if (res != 0) {
			return res;
		}
		for (int i = 0; i < Math.min(arg0.segmentCount(), arg1.segmentCount()); i++) {
			res = arg0.segment(i).compareTo(arg1.segment(i));
			if (res != 0) {
				return res;
			}
		}
		return arg0.segmentCount() - arg1.segmentCount();
