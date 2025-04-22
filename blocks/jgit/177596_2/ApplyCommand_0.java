			try (OutputStream out = buffer) {
				for (Iterator<ByteBuffer> l = newLines.iterator(); l
						.hasNext();) {
					ByteBuffer line = l.next();
					if (line == null) {
						break;
					}
					out.write(line.array()
							line.limit() - line.position());
