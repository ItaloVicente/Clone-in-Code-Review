public class CleanCommand extends GitCommand<Status> {
	private LinkedList<String> paths;

	private WorkingTreeIterator workingTreeIt;

	private Set<String> retUntracked = null;

	private IndexDiff diff = null;
