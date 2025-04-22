		if (db.getObjectDatabase() instanceof ObjectDirectory) {
			ObjectDirectory odb = (ObjectDirectory) db.getObjectDatabase();
			NullProgressMonitor m = NullProgressMonitor.INSTANCE;

			final File pack
			PackWriter pw = new PackWriter(db);
			try {
				Set<ObjectId> all = new HashSet<ObjectId>();
				for (Ref r : db.getAllRefs().values())
					all.add(r.getObjectId());
				pw.preparePack(m

				final ObjectId name = pw.computeName();
				OutputStream out;

				pack = nameFor(odb
				out = new BufferedOutputStream(new FileOutputStream(pack));
				try {
					pw.writePack(m
				} finally {
					out.close();
				}
				pack.setReadOnly();

				idx = nameFor(odb
				out = new BufferedOutputStream(new FileOutputStream(idx));
				try {
					pw.writeIndex(out);
				} finally {
					out.close();
				}
				idx.setReadOnly();
			} finally {
				pw.release();
			}
