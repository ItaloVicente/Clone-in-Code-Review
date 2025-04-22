		if (blocks != null) {
			int outPtr = 0;
			for (final Block b : blocks) {
				System.arraycopy(b.buffer, 0, out, outPtr, b.count);
				outPtr += b.count;
			}
		} else {
			final FileInputStream in = new FileInputStream(onDiskFile);
			try {
				IO.readFully(in, out, 0, (int) len);
			} finally {
				in.close();
			}
