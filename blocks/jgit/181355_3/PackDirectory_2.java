				if (packer.getFindBestPackRepresentation() || representationAttemptCount < searchForReuseMaxPackFilesToScan) {
					try {
						LocalObjectRepresentation rep = p.representation(curs
						p.resetTransientErrorCount();
						if (rep != null) {
							packer.select(otp
						}
					} catch (PackMismatchException e) {
						pList = scanPacks(pList);
						continue SEARCH;
					} catch (IOException e) {
						handlePackError(e
