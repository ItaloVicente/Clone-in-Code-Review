		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					if (p.hasObject(objectId))
						return true;
				} catch (IOException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().unableToReadPackfile,
							p.getPackFile().getAbsolutePath()), e);
					removePack(p);
				}
			}
		} while (searchPacksAgain(pList));
		return false;
