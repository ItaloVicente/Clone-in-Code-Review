			synchronized (LAST_DIR_SEMAPHORE) {
				if (Objects.equals(dir
					return lastDirDuration;
				}
			}

			final Duration duration = determineFsTimestampResolution(dir);
			synchronized (LAST_DIR_SEMAPHORE) {
				lastDir = dir;
				lastDirDuration = duration;
			}
			return duration;
		}

		private static Duration determineFsTimestampResolution(Path dir) {
