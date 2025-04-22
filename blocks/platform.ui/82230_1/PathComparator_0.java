		if (arg0 == arg1) {
			return 0;
		}
		int res = 0;
		String device0 = arg0.getDevice();
		String device1 = arg1.getDevice();
		if (device0 != null && device1 == null) {
			return +1;
		}
		if (device0 == null && device1 != null) {
			return -1;
		}
		if (device0 != null && device1 != null) {
			res = device0.compareTo(device1);
			if (res != 0 && !device0.equalsIgnoreCase(device1)) {
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
