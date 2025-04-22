		MappedByteBuffer map;
		try {
			map = fd.getChannel().map(MapMode.READ_ONLY, pos, size);
		} catch (IOException ioe1) {
			System.gc();
			System.runFinalization();
			map = fd.getChannel().map(MapMode.READ_ONLY, pos, size);
		}
