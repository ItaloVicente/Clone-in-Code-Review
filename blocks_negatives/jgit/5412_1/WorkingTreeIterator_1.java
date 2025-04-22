				if (!mightNeedCleaning())
					return computeHash(is, len);

				if (len <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
					ByteBuffer rawbuf = IO.readWholeStream(is, (int) len);
					byte[] raw = rawbuf.array();
					int n = rawbuf.limit();
					if (!isBinary(raw, n)) {
						rawbuf = filterClean(raw, n);
						raw = rawbuf.array();
						n = rawbuf.limit();
					}
					return computeHash(new ByteArrayInputStream(raw, 0, n), n);
				}

				if (isBinary(e))
					return computeHash(is, len);

				final long canonLen;
				final InputStream lenIs = filterClean(e.openInputStream());
				try {
					canonLen = computeLength(lenIs);
				} finally {
					safeClose(lenIs);
				}

				return computeHash(filterClean(is), canonLen);
