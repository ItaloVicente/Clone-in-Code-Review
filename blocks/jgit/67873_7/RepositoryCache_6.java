			try {
				File commonDir = FileUtils.getCommonDir(dir);
				if (commonDir == null) {
					commonDir = dir;
				}
				return fs.resolve(commonDir
						&& fs.resolve(commonDir
						&& isValidHead(
								new File(dir
										Constants.HEAD));
			} catch (IOException e) {
				return false;
			}
