		ObjectWalk ow = null;
		try {
			if (shallowPack) {
				ow = new DepthWalk.ObjectWalk(reader
			} else {
				ow = new ObjectWalk(reader);
			}
			ow.assumeShallow(shallow);
			preparePack(countingMonitor
		} finally {
			if (ow != null) {
				ow.close();
			}
