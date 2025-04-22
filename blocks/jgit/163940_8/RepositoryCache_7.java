			File commonDir;
			try {
				commonDir = fs.getCommonDir(dir);
			} catch (IOException e) {
				commonDir = null;
			}
			if (commonDir == null) {
				commonDir = dir;
			}
			return fs.resolve(commonDir
					&& fs.resolve(commonDir
					&& (fs.resolve(commonDir
