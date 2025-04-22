		ZipInputStream in = new ZipInputStream(
				new ByteArrayInputStream(zipData));

		ZipEntry e;
		while ((e = in.getNextEntry()) != null)
			l.add(e.getName());
		in.close();
