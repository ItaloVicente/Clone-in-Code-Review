		FilenameFilter projectFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.equals(".project");
			}
		};
