			this.cannotBeRacilyClean = notRacyClean(lastRead);
		}

		private boolean notRacyClean(final long read) {
			return read - lastModified > 2 * 60 * 1000L;
		}

		PackList updateLastRead(final long now) {
			if (notRacyClean(now))
				cannotBeRacilyClean = true;
			lastRead = now;
			return this;
		}

		boolean tryAgain(final long currLastModified) {
			if (lastModified != currLastModified)
				return true;

			if (cannotBeRacilyClean)
				return false;

			if (notRacyClean(lastRead)) {
				return false;
			}

			return true;
