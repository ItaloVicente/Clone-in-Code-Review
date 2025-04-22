		public FileEntry(File f
				FileModeStrategy fileModeStrategy) {
			this.fs = fs;
			this.attributes = attributes;
			f = fs.normalize(f);
			mode = fileModeStrategy.getMode(f
		}

