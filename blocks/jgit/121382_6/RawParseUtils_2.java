		IntList map;
		try {
		  map = lineMapOrBinary(buf
		} catch (BinaryBlobException e) {
			map = new IntList(3);
			map.add(Integer.MIN_VALUE);
			map.add(ptr);
			map.add(end);
		}
		return map;
	}
