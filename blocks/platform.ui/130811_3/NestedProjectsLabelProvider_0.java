	private CompletableFuture<Map<IContainer, Integer>> refreshSeverities(Set<IResource> dirty) {
		if (dirty != null) {
			supplier.markDirty(dirty);
		}
		return CompletableFuture.supplyAsync(supplier);
	}

	private final class ProblemsModelSupplier implements Supplier<Map<IContainer, Integer>> {
		private Set<IResource> dirty = null;
		private Map<IContainer, Integer> cache = new HashMap<>();

		@Override
		public Map<IContainer, Integer> get() {
