	private String sourcePath;

	private Map<Integer, Integer> sourceLines = new HashMap<Integer, Integer>();

	private Map<RevCommit, Diff> diffToParentCommit = new HashMap<RevCommit, Diff>();

