			switch (getOptions().getAutoCRLF()) {
			case INPUT:
			case TRUE:
				InputStream dcIn = null;
				try {
					ObjectLoader loader = repository.open(entry.getObjectId());
					dcIn = new EolCanonicalizingInputStream(
							loader.openStream()
					long dcInLen = computeLength(dcIn);
					dcIn.close();
					dcIn = new EolCanonicalizingInputStream(
							loader.openStream()
					byte[] autoCrLfHash = computeHash(dcIn
					return getEntryObjectId().compareTo(autoCrLfHash
				} catch (IOException e) {
					return false;
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
