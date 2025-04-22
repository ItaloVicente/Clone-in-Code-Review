		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					if (p.hasObject(objectId))
						return true;
				} catch (IOException e) {
					removePack(p);
				}
			}
		} while (searchPacksAgain(pList));
		return false;
