
		private static void appendToFile(File file
				throws IOException {
			FileOutputStream fos = new FileOutputStream(file
			try {
				fos.write(content.getBytes(Constants.CHARACTER_ENCODING));
				fos.write('\n');
			} finally {
				fos.close();
			}
		}
