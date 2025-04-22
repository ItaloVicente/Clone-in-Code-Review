				initializeDigestAndReadBuffer();

				final long len = e.getLength();
				if (!mightNeedCleaning(e))
					return computeHash(is

				if (len <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
					ByteBuffer rawbuf = IO.readWholeStream(is
					byte[] raw = rawbuf.array();
					int n = rawbuf.limit();
					if (!isBinary(e
						rawbuf = filterClean(e
						raw = rawbuf.array();
						n = rawbuf.limit();
					}
					return computeHash(new ByteArrayInputStream(raw
