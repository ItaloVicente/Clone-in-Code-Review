        }

	private void simulatePackfileRemoval() throws IOException {
		for (PackFile packFile : db.getObjectDatabase().getPacks()) {
			if (packFile.getBitmapIndex() != null) {
				packFile.getPackFile().delete();
			}
		}
