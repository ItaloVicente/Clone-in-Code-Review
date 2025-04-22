
	private void fileChangedWatchService(File file) {
		while (file.isFile()) {
			file = file.getParentFile();
		}
		try {
			if (watcher != null) {
				watcher.close();
			}
			watcher = FileSystems.getDefault().newWatchService();
			final Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
			path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			new Thread(() -> {
				try {
					WatchKey key = watcher.take();
					while (key != null) {
						for (WatchEvent<?> event : key.pollEvents()) {
							final Path changedPath = (Path) event.context();
							Display.getDefault().asyncExec(() -> {
								if (browser.getUrl().endsWith(changedPath.toString())) {
									browser.refresh();
								}
							});
						}
						key.reset();
						key = watcher.take();
					}
				} catch (InterruptedException | ClosedWatchServiceException e) {
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
