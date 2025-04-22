		private boolean cannotBeRacilyClean;

		PackList(final long lastRead, final long lastModified,
				final PackFile[] packs) {
			this.lastRead = lastRead;
			this.lastModified = lastModified;
