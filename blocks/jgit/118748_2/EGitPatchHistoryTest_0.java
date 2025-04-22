			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(proc.getInputStream()
				String commitId = null;
				TemporaryBuffer buf = null;
				for (;;) {
					String line = in.readLine();
					if (line == null)
						break;
					if (line.startsWith("commit ")) {
						if (buf != null) {
							buf.close();
							onCommit(commitId
							buf.destroy();
						}
						commitId = line.substring("commit ".length());
						buf = new TemporaryBuffer.LocalFile(null);
					} else if (buf != null) {
						buf.write(line.getBytes(ISO_8859_1));
						buf.write('\n');
