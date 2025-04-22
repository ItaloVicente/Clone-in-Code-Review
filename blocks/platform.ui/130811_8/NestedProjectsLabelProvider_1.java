	private CompletableFuture<Map<IResource, Integer>> refreshSeverities(Set<IResource> dirty) {
		if (dirty != null) {
			supplier.markDirty(dirty);
		}
		return CompletableFuture.supplyAsync(supplier);
	}

	private final class ProblemsModelSupplier implements Supplier<Map<IResource, Integer>> {
		private Set<IResource> dirty = null;
		private Map<IResource, Integer> cache = new HashMap<>();

		@Override
		public Map<IResource, Integer> get() {
			Set<IResource> currentDirty = null;
			if (dirty != null) {
				currentDirty = new HashSet<>();
				currentDirty.addAll(this.dirty);
				this.dirty.removeAll(currentDirty);
				removeFromCache(currentDirty);
			}

