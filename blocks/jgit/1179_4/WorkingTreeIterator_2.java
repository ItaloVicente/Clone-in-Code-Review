				initializeDigestAndReadBuffer();

				final long length = e.getLength();
				if (!options.isAutoCRLF())
					return calculateHash(is

				if (length <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
					final byte[] bytes = IO.readFully(is
					if (RawText.isBinary(bytes))
						return calculateHash(new ByteArrayInputStream(bytes)
								length);

					final InputStream lengthIs = new ByteArrayInputStream(bytes);
					final long canonicalLength = determineCanonicalizedLength(lengthIs);

					final InputStream contentIs = new EolCanonicalizingInputStream(
							new ByteArrayInputStream(bytes));
					return calculateHash(contentIs
				}

				final InputStream binaryIs = e.openInputStream();
				try {
					if (RawText.isBinary(binaryIs))
						return calculateHash(is
				} finally {
					binaryIs.close();
