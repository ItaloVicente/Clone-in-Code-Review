	void selectObjectRepresentation(PackWriter packer
			WindowCursor curs) throws IOException {
		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (final PackFile p : pList.packs) {
				try {
					LocalObjectRepresentation rep = p.representation(curs
					if (rep != null)
						packer.select(otp
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					removePack(p);
				}
			}
			break SEARCH;
		}

		for (AlternateHandle h : myAlternates())
			h.db.selectObjectRepresentation(packer
	}

	boolean hasObject2(final String objectName) {
		return fileFor(objectName).exists();
	}

	ObjectLoader openObject2(final WindowCursor curs
