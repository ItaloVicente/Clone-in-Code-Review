			try {
				final BufferedReader br = openReader(path);
				try {
					line = br.readLine();
				} finally {
					br.close();
				}
