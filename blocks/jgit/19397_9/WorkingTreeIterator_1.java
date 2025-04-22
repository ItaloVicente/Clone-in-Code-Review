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
							loader.openStream()
					long dcInLen;
					try {
						dcInLen = computeLength(dcIn);
					} catch (EolCanonicalizingInputStream.IsBinaryException e) {
						entry.setLength(loader.getSize());
						return true;
					} finally {
						dcIn.close();
					}

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream()
					byte[] autoCrLfHash = computeHash(dcIn
					boolean changed = getEntryObjectId().compareTo(
							autoCrLfHash
					if (!changed) {
						entry.setLength((int) getEntryLength());
						entry.setObjectIdFromRaw(autoCrLfHash
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
