		final File idx = nameFor(odb, name, ".idx");
		out = new BufferedOutputStream(new FileOutputStream(idx));
		try {
			pw.writeIndex(out);
		} finally {
			out.close();
