			final ObjectId name = pw.computeName();
			final File packFile = fullPackFileName(name
			final File idxFile = fullPackFileName(name
			final File[] files = new File[] { packFile
			write(files
			return files;
		}
