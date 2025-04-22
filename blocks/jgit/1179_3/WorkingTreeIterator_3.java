
				final long canonicalLength;
				final InputStream lengthIs = e.openInputStream();
				try {
					canonicalLength = determineCanonicalizedLength(lengthIs);
				} finally {
					lengthIs.close();
