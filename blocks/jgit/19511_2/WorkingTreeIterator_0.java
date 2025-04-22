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
