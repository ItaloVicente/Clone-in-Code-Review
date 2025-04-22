		/**
		 * Create a record for a project based on the info in the file.
		 *
		 * @param file
		 * @param aRepository
		 *            relative path to repository
		 */
		ProjectRecord(File file, File aRepository) {
			projectSystemFile = file;
			setProjectName();
			repository = aRepository;
		}

