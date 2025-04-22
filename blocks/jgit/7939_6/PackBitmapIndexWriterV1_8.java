
		writeHeader(bitmaps.getOptions()
		writeBody(bitmaps);
		writeFooter();

		out.flush();
	}

	private void writeHeader(byte options
			throws IOException {
		byte[] tmp = new byte[4];
		NB.encodeInt32(tmp
		out.write(tmp
		out.write(options);
		out.write(packDataChecksum);
