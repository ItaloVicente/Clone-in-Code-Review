			@Override
			public void run() {
				RepositorySearchDialog sd = new RepositorySearchDialog(
						getSite().getShell(), getDirs());
				if (sd.open() == Window.OK) {
					Set<String> dirs = new HashSet<String>();
					dirs.addAll(getDirs());
					if (dirs.addAll(sd.getDirectories()))
						saveDirs(dirs);
					scheduleRefresh();
				}
