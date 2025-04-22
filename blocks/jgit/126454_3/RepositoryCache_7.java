			try {
				File commonDir = fs.getCommonDir(dir);
				if (commonDir == null) {
					commonDir = dir;
				}
				return fs.resolve(commonDir
						&& fs.resolve(commonDir
						&& isValidHead(new File(dir
			} catch (IOException e) {
				return false;
			}
