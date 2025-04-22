		if (list.size() <= 58000) {
			buf = new TemporaryBuffer.Heap(2 << 20);
			index(buf, packHash, list);
			packIndex = PackIndex.read(buf.openInputStream());
		}

		try (DfsOutputStream os = db.writeFile(pack, INDEX)) {
			CountingOutputStream cnt = new CountingOutputStream(os);
			if (buf != null)
