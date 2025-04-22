					dcIn = new EolCanonicalizingInputStream(
							loader.openStream(), true);
					byte[] autoCrLfHash = computeHash(dcIn, dcInLen);
					boolean changed = getEntryObjectId().compareTo(
							autoCrLfHash, 0) != 0;
					return changed;
