	static final class Block {
		final List<DeltaTask> tasks;
		final PackConfig config;
		final ObjectReader templateReader;
		final DeltaCache dc;
		final ThreadSafeProgressMonitor pm;
		final ObjectToPack[] list;
		final int beginIndex;
		final int endIndex;
