/**
 * This class passes information about a changed Git index to a
 * {@link RepositoryListener}
 *
 * Currently only a reference to the repository is passed.
 */
public class RefsChangedEvent extends RepositoryChangedEvent {
	/**
	 * Create an event describing reference changes in a repository.
	 *
	 * @param repository
	 *            the repository whose references recently changed.
	 */
	public RefsChangedEvent(final Repository repository) {
		super(repository);
