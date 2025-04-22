			final File pack = nameFor(odb, name, ".pack");
			out = new BufferedOutputStream(new FileOutputStream(pack));
			try {
				pw.writePack(out);
			} finally {
				out.close();
			}
			pack.setReadOnly();
