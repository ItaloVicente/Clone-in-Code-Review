			if (Objects.equals(dir
				return lastDirDuration;
			}

			final Duration duration = determineFsTimestampResolution(dir);
			lastDir = dir;
			lastDirDuration = duration;
			return duration;
		}

		private static Duration determineFsTimestampResolution(Path dir) {
