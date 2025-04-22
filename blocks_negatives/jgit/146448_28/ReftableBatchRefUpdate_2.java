/**
 * {@link org.eclipse.jgit.lib.BatchRefUpdate} for
 * {@link org.eclipse.jgit.internal.storage.dfs.DfsReftableDatabase}.
 */
public class ReftableBatchRefUpdate extends BatchRefUpdate {
	private static final int AVG_BYTES = 36;
