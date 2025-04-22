    private FileFilter projectFilter = new FileFilter() {
        @Override
		public boolean accept(File pathName) {
            return pathName.getName().equals(
                    IProjectDescription.DESCRIPTION_FILE_NAME);
        }
    };
