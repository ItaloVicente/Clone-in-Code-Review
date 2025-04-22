=======
public class CleanCommand extends GitCommand<Status> {
	private LinkedList<String> paths;

	private WorkingTreeIterator workingTreeIt;

	private Set<String> retUntracked = null;

	private IndexDiff diff = null;

	protected CleanCommand(Repository repo) throws IOException {
		super(repo);
		if (workingTreeIt == null)
			workingTreeIt = new FileTreeIterator(repo);

		diff = new IndexDiff(repo
		diff.diff();
	}

	public Set<String> dirContents() throws IOException {
		retUntracked = new Status(diff).getUntracked();

		return retUntracked;
	}

	public Status call() {
		if (retUntracked.isEmpty() == true)
			return null;

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		for (String i : retUntracked) {
			try {
				FileUtils.delete(new File(i)
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return new Status(diff);
	}

	public void setPaths(LinkedList<String> paths) {
		this.paths = paths;
	}

	public LinkedList<String> getPaths() {
		return paths;
	}
>>>>>>> 020f5f7... First submission of CleanCommand
