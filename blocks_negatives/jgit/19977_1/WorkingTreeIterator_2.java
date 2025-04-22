				return true;
			switch (getOptions().getAutoCRLF()) {
			case INPUT:
			case TRUE:
				InputStream dcIn = null;
				try {
					ObjectLoader loader = reader.open(entry.getObjectId());
					if (loader == null)
						return true;

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream(), true, true /* abort if binary */);
					long dcInLen;
					try {
						dcInLen = computeLength(dcIn);
					} catch (EolCanonicalizingInputStream.IsBinaryException e) {
						entry.setLength(entry.getLength());
						return true;
					} finally {
						dcIn.close();
					}

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream(), true);
					byte[] autoCrLfHash = computeHash(dcIn, dcInLen);
					boolean changed = getEntryObjectId().compareTo(
							autoCrLfHash, 0) != 0;
					if (!changed) {
						entry.setObjectIdFromRaw(autoCrLfHash, 0);
					}
					entry.setLength(loader.getSize());
					return changed;
				} catch (IOException e) {
					return true;
				} finally {
					if (dcIn != null)
						try {
							dcIn.close();
						} catch (IOException e) {
						}
				}
			case FALSE:
				try {
					ObjectLoader loader = reader.open(entry.getObjectId());
					if (loader != null)
						entry.setLength((int) loader.getSize());
				} catch (IOException e) {
				}
				break;
			}
