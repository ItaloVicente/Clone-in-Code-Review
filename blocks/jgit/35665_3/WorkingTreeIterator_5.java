
				dcIn = loader.openStream();
				if (autoCRLF == AutoCRLF.TRUE || autoCRLF == AutoCRLF.INPUT)
					dcIn = new EolCanonicalizingInputStream(dcIn

				if (hasIdentSet)
					dcIn = new IdentInputStream(dcIn

				byte[] autoCrLfHash = computeHash(dcIn
				boolean changed = getEntryObjectId().compareTo(autoCrLfHash
				return changed;
			} catch (IOException e) {
				return true;
			} finally {
				if (dcIn != null)
					try {
						dcIn.close();
					} catch (IOException e) {
					}
