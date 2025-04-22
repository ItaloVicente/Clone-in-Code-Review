		for (int i = 0; i < Math.min(arg0.segmentCount(), arg1.segmentCount()); i++) {
			int res = arg0.segment(i).compareTo(arg1.segment(i));
			if (res != 0) {
				return res;
			}
		}
		return arg0.segmentCount() - arg1.segmentCount();
