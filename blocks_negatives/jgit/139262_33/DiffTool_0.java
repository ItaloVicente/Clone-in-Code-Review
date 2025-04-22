				FileElement local = new FileElement(ent.getOldPath(),
						ent.getOldId().name(),
						getObjectStream(sourcePair, Side.OLD, ent));
				FileElement remote = new FileElement(ent.getNewPath(),
						ent.getNewId().name(),
						getObjectStream(sourcePair, Side.NEW, ent));
