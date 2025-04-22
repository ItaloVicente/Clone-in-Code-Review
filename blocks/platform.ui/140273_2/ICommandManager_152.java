	@Deprecated
	void addCommandManagerListener(ICommandManagerListener commandManagerListener);

	@Deprecated
	Set getActiveContextIds();

	@Deprecated
	String getActiveKeyConfigurationId();

	@Deprecated
	String getActiveLocale();

	@Deprecated
	String getActivePlatform();

	@Deprecated
	ICategory getCategory(String categoryId);

	@Deprecated
	ICommand getCommand(String commandId);

	@Deprecated
	Set getDefinedCategoryIds();

	@Deprecated
	Set getDefinedCommandIds();

	@Deprecated
	Set getDefinedKeyConfigurationIds();

	@Deprecated
	IKeyConfiguration getKeyConfiguration(String keyConfigurationId);

	@Deprecated
	Map getPartialMatches(KeySequence keySequence);

	@Deprecated
	String getPerfectMatch(KeySequence keySequence);

	@Deprecated
	boolean isPartialMatch(KeySequence keySequence);

	@Deprecated
	boolean isPerfectMatch(KeySequence keySequence);

	@Deprecated
	void removeCommandManagerListener(ICommandManagerListener commandManagerListener);
