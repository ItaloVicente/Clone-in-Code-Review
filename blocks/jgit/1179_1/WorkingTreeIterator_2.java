				final boolean canonicalizeEols;
				if (canonicalizeEolsForIdCalculation) {
					final InputStream is = e.openInputStream();
					try {
						canonicalizeEols = !RawText.isBinary(is);
					} finally {
						is.close();
					}
				}
				else {
					canonicalizeEols = false;
				}

				final long blobLength;
				if (!canonicalizeEols) {
					blobLength = e.getLength();
				}
				else {
					final InputStream is = e.openInputStream();
					try {
						blobLength = determineCanonicalizedLength(is);
					}
					finally {
						is.close();
					}
				}

