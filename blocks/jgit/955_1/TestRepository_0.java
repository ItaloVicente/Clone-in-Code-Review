			Set<ObjectId> all = new HashSet<ObjectId>();
			for (Ref r : db.getAllRefs().values())
				all.add(r.getObjectId());
			pw.preparePack(all

			final ObjectId name = pw.computeName();
			OutputStream out;

			final File pack = nameFor(odb
			out = new BufferedOutputStream(new FileOutputStream(pack));
			try {
				pw.writePack(out);
			} finally {
				out.close();
			}
			pack.setReadOnly();

			final File idx = nameFor(odb
			out = new BufferedOutputStream(new FileOutputStream(idx));
			try {
				pw.writeIndex(out);
			} finally {
				out.close();
			}
			idx.setReadOnly();
