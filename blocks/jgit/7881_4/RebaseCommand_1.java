				File amendFile = new File(rebaseDir
				boolean amendExists = amendFile.exists();
				if (amendExists) {
					FileUtils.delete(amendFile);
				}
				if (newHead == null && !amendExists) {
