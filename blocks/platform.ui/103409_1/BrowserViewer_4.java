
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
							if (changedPath.toString().endsWith(".html")) { //$NON-NLS-1$
								Display.getDefault().asyncExec(() -> browser.refresh());
							}
						}
						key.reset();
						key = watcher.take();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClosedWatchServiceException e) {
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
