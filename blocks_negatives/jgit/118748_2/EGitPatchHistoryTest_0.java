			final BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream(), ISO_8859_1));
			String commitId = null;
			TemporaryBuffer buf = null;
			for (;;) {
				String line = in.readLine();
				if (line == null)
					break;
				if (line.startsWith("commit ")) {
					if (buf != null) {
						buf.close();
						onCommit(commitId, buf.toByteArray());
						buf.destroy();
