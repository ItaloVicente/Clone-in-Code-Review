		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (PackFile p : pList.packs) {
				try {
					LocalObjectRepresentation rep = p.representation(curs, otp);
					p.resetTransientErrorCount();
					if (rep != null)
						packer.select(otp, rep);
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					handlePackError(e, p);
				}
			}
			break SEARCH;
		}
