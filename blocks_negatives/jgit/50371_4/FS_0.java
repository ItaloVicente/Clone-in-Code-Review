			if (stdinArgs != null) {
				final PrintWriter stdinWriter = new PrintWriter(
						process.getOutputStream());
				stdinWriter.print(stdinArgs);
				stdinWriter.flush();
				stdinWriter.close();
