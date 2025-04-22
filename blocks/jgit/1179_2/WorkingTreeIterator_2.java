				initializeDigestAndReadBuffer();

				final long length = e.getLength();
				if (!options.isAutocrlf())
					return calculateHash(is

				if (length <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
					final byte[] bytes = IO.readFully(is
					if (RawText.isBinary(new ByteArrayInputStream(bytes)))
						return calculateHash(new ByteArrayInputStream(bytes)

					final long canonicalLength = determineCanonicalizedLength(new ByteArrayInputStream(
							bytes));
					return calculateHash(new EolCanonicalizingInputStream(
							new ByteArrayInputStream(bytes))
				}

				final InputStream binaryIs = e.openInputStream();
				try {
					if (RawText.isBinary(binaryIs))
						return calculateHash(is
				} finally {
					binaryIs.close();
