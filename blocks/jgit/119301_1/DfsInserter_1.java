		try (TemporaryBuffer.Heap buf = maybeGetTemporaryBuffer(list);
				DfsOutputStream os = db.writeFile(pack
				CountingOutputStream cnt = new CountingOutputStream(os)) {
			if (buf != null) {
				index(buf
				packIndex = PackIndex.read(buf.openInputStream());
