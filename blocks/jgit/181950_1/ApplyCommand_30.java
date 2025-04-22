			oldLines.add(null);
		}
		if (oldLines.equals(newLines)) {
		}

		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try {
			try (OutputStream out = buffer) {
				for (Iterator<ByteBuffer> l = newLines.iterator(); l
						.hasNext();) {
					ByteBuffer line = l.next();
					if (line == null) {
						break;
					}
					out.write(line.array()
					if (l.hasNext()) {
						out.write('\n');
					}
