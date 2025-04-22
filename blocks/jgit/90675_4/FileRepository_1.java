			if (!lck.lock()) {
				return;
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		try {
			FileTime lastModified = Files.getLastModifiedTime(gcLog.toPath());
			if (lastModified.toInstant().compareTo(getLogExpiry()) < 0) {
				try {
					if (!background) {
						String oldError = new String(Files.readAllBytes(gcLog.toPath()));
						throw new JGitInternalException("A previous GC run reported an error: " +
								oldError +
								"\nAutomatic gc will fail until the file " + gcLog + " is removed");
					}
				} finally {
					lck.unlock();
				}
				return;
			}
		} catch (NoSuchFileException e) {
		} catch (IOException | ParseException e) {
			throw new JGitInternalException(e.getMessage()
		}

		Runnable gcTask = () -> {
			try {
					GC gc = new GC(this);
					gc.setPackConfig(new PackConfig(this));
					gc.setProgressMonitor(monitor);
					gc.setAuto(true);
					gc.gc();
					lck.unlock();
					gcLog.delete();

			} catch (IOException | ParseException e) {
				if (background) {
					try {
						lck.write(e.getMessage().getBytes());
						lck.commit();
					} catch (IOException e2) {
						e2.addSuppressed(e);
						LOG.error(e2.getMessage()
						lck.unlock();
					}
				} else {
					throw new JGitInternalException(e.getMessage()
				}
			}
		};
		if (background) {
			Thread thread = new Thread(gcTask);
			thread.start();
		} else {
			gcTask.run();
