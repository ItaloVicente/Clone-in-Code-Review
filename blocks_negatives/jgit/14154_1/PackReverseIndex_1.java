		int nth = 0;
		for (final MutableEntry me : index) {
			final long o = me.getOffset();
			if (o <= Integer.MAX_VALUE)
				nth32[Arrays.binarySearch(offsets32, (int) o)] = nth++;
			else
				nth64[Arrays.binarySearch(offsets64, o)] = nth++;
