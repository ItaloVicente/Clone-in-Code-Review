			try {
				FileRepository fileRep = new FileRepository(dir);
				return fs.resolve(fileRep.getCommonDirectory()
						&& fs.resolve(fileRep.getCommonDirectory()
						&& isValidHead(
								new File(fileRep.getDirectory()
										Constants.HEAD));
			} catch (IOException e) {
				return false;
			}
