		byte[] lastHunkBuffer = Arrays.copyOfRange(lastHunk.getBuffer()
				lastHunk.getStartOffset()
		RawText lhrt = new RawText(lastHunkBuffer);
		return lhrt.getString(lhrt.size() - 1)

	}

	private static long copyStreams(InputStream from
			throws IOException {
		byte[] buf = new byte[4 * 1024];
		long total = 0;
		while (true) {
			int r = from.read(buf);
			if (r == -1) {
				break;
			}
			to.write(buf
			total += r;
		}
		return total;
