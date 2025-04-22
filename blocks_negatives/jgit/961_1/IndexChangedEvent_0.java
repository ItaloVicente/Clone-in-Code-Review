/**
 * This class passes information about a changed Git index to a
 * {@link RepositoryListener}
 *
 * Currently only a reference to the repository is passed.
 */
public class IndexChangedEvent extends RepositoryChangedEvent {
	/**
	 * Create an event describing index changes in a repository.
	 *
	 * @param repository
	 *            the repository whose index (DirCache) recently changed.
	 */
	public IndexChangedEvent(final Repository repository) {
		super(repository);
