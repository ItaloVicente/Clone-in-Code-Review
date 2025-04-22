=======
	private void simulatePackfileRemoval() throws IOException {
		for (PackFile packFile : db.getObjectDatabase().getPacks()) {
			if (packFile.getBitmapIndex() != null) {
				packFile.getPackFile().delete();
			}
		}
>>>>>>> 90400ca1e... Verify packfile existence when returned from WindowCursor
