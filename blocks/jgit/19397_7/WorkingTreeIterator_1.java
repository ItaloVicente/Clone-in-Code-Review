			switch (getOptions().getAutoCRLF()) {
			case INPUT:
			case TRUE:
				InputStream dcIn = null;
				try {
					ObjectLoader loader = repository.open(entry.getObjectId());
					dcIn = loader.openStream();
					if (RawText.isBinary(dcIn))
						return true;
					dcIn.close();

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream()
					long dcInLen = computeLength(dcIn);
					dcIn.close();

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream()
					byte[] autoCrLfHash = computeHash(dcIn
					boolean changed = getEntryObjectId().compareTo(
							autoCrLfHash
					if (!changed) {
						entry.setLength((int) getEntryLength());
						entry.setObjectIdFromRaw(autoCrLfHash
					}
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
				break;
			}
