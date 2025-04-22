				return true;

			switch (getEolStreamType()) {
			case DIRECT:
				return true;
			default:
				try {
					ObjectLoader loader = reader.open(entry.getObjectId());
					if (loader == null)
						return true;

					long dcInLen;
					try (InputStream dcIn = new AutoLFInputStream(
							loader.openStream(), true,
							true /* abort if binary */)) {
						dcInLen = computeLength(dcIn);
					} catch (AutoLFInputStream.IsBinaryException e) {
						return true;
					}

					try (InputStream dcIn = new AutoLFInputStream(
							loader.openStream(), true)) {
						byte[] autoCrLfHash = computeHash(dcIn, dcInLen);
						boolean changed = getEntryObjectId()
								.compareTo(autoCrLfHash, 0) != 0;
						return changed;
					}
				} catch (IOException e) {
					return true;
				}
			}
