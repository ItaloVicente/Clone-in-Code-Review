			fileFilter = new IFileStoreFilter() {

				@Override
				public boolean accept(IFileStore file) {
					if (!file.fetchInfo().isDirectory() && showFiles == false) {
						return false;
					}
					return true;
