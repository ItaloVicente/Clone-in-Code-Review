			final File pack
			PackWriter pw = new PackWriter(db
			try {
				Set<ObjectId> all = new HashSet<ObjectId>();
				for (Ref r : db.getAllRefs().values())
					all.add(r.getObjectId());
				pw.preparePack(all
