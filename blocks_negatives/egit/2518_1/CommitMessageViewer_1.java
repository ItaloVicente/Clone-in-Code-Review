	private void addDiff(final StringBuilder d,
			final ArrayList<StyleRange> styles) {
		final DiffFormatter diffFmt = new DiffFormatter(
				new BufferedOutputStream(new ByteArrayOutputStream() {

					@Override
					public synchronized void write(byte[] b, int off, int len) {
						super.write(b, off, len);
						if (currentEncoding == null)
							d.append(toString());

						else
							try {
								d.append(toString(currentEncoding));
							} catch (UnsupportedEncodingException e) {
								d.append(toString());
							}
						reset();
					}
