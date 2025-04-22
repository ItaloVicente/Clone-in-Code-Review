
		if (dstBase != null) {
			final File dir = dstBase.getParentFile();
			final String nam = dstBase.getName();
			dstPack = new File(dir, nam + ".pack");
			dstIdx = new File(dir, nam + ".idx");
			packOut = new RandomAccessFile(dstPack, "rw");
			packOut.setLength(0);
		} else {
			dstPack = null;
			dstIdx = null;
		}
