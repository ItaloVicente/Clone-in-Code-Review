	private static class CopyFile {
		final String src;
		final String dest;

		CopyFile(String path
			this.src = path + "/" + src;
			this.dest = dest;
		}

		void copy() throws IOException {
			FileInputStream input = new FileInputStream(src);
			FileOutputStream output = new FileOutputStream(dest);
			FileChannel channel = input.getChannel();
			output.getChannel().transferFrom(channel
			input.close();
			output.close();
		}
	}

