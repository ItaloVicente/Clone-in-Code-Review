	private final String branch;

	private final String repositoryName;

	private boolean tracked = false;

	private boolean ignored = false;

	private boolean dirty = false;

	private boolean conflicts = false;

	private boolean assumeValid = false;

	private Staged staged = Staged.NOT_STAGED;

