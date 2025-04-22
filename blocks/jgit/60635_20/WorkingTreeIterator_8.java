					try (InputStream dcIn = new AutoLFInputStream(
							loader.openStream()
						byte[] autoCrLfHash = computeHash(dcIn
						boolean changed = getEntryObjectId()
								.compareTo(autoCrLfHash
						return changed;
					}
