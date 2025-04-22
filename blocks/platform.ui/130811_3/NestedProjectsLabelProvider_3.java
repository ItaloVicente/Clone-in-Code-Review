			} catch (CancellationException e) {
				return Collections.emptyMap();
			}
			return cache;
		}

		public void markDirty(Set<IResource> dirty) {
			if (this.dirty == null) {
				this.dirty = new HashSet<>();
