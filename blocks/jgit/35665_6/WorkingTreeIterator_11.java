
				dcIn = filterClean(loader.openStream()

				byte[] autoCrLfHash = computeHash(dcIn
				boolean changed = entryObjectId.compareTo(autoCrLfHash
				return changed;
			} catch (IOException e) {
				return true;
			} finally {
				if (dcIn != null)
					try {
						dcIn.close();
					} catch (IOException e) {
					}
