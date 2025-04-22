				initializeDigest();

				contentDigest.reset();
				contentDigest.update(hblob);
				contentDigest.update((byte) ' ');

				final long blobLength = e.getLength();
				long sz = blobLength;
				if (sz == 0) {
					contentDigest.update((byte) '0');
				} else {
					final int bufn = contentReadBuffer.length;
					int p = bufn;
					do {
						contentReadBuffer[--p] = digits[(int) (sz % 10)];
						sz /= 10;
					} while (sz > 0);
					contentDigest.update(contentReadBuffer, p, bufn - p);
