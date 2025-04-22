	private static class CopyFile {
		final String src;
		final String dest;
		final String relativeDest;

		CopyFile(Repository repo
			this.relativeDest = dest;
		}

		void copy() throws IOException {
			FileInputStream input = new FileInputStream(src);
			FileOutputStream output = new FileOutputStream(dest);
			try {
				FileChannel channel = input.getChannel();
				output.getChannel().transferFrom(channel
			} finally {
				input.close();
				output.close();
			}
		}
	}

