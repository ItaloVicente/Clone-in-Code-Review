			boolean tryAgain;
			do {
				tryAgain = false;
				try {
					Files.delete(p);
					return;
				} catch (NoSuchFileException | FileNotFoundException e) {
					handleDeleteException(f
							SKIP_MISSING | IGNORE_ERRORS);
					return;
				} catch (DirectoryNotEmptyException e) {
					handleDeleteException(f
					return;
				} catch (IOException e) {
					if (!f.canWrite()) {
						tryAgain = f.setWritable(true);
					}
					if (!tryAgain) {
						t = e;
					}
