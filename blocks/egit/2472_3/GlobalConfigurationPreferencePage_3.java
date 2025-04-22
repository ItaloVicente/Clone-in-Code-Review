	private StackLayout repoConfigStackLayout;

	private List<Repository> repositories;

	private Map<Repository, ConfigurationEditorComponent> repoConfigEditors = new HashMap<Repository, ConfigurationEditorComponent>();

	private Set<Repository> dirtyRepositories = new HashSet<Repository>();

