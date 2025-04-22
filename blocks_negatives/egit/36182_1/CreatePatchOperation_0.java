		final DiffFormatter diffFmt = new DiffFormatter(
				new ByteArrayOutputStream() {

					@Override
					public synchronized void write(byte[] b, int off, int len) {
						super.write(b, off, len);
						try {
							if (currentEncoding == null)
							else
								sb.append(toString(currentEncoding));
						} catch (UnsupportedEncodingException e) {
							sb.append(toString());
						}
						reset();
					}
				}) {
