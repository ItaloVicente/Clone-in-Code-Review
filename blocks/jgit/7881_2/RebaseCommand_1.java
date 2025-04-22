				File amendFile = new File(rebaseDir
				boolean ammendExists = amendFile.exists();
				if (ammendExists) {
					FileUtils.delete(amendFile);
				}
				if (newHead == null && !ammendExists) {
