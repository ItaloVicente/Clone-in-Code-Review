	void add(IAction action);

	void add(IContributionItem item);

	void appendToGroup(String groupName, IAction action);

	void appendToGroup(String groupName, IContributionItem item);

	IContributionItem find(String id);

	IContributionItem[] getItems();

	IContributionManagerOverrides getOverrides();

	void insertAfter(String id, IAction action);

	void insertAfter(String id, IContributionItem item);

	void insertBefore(String id, IAction action);

	void insertBefore(String id, IContributionItem item);

	boolean isDirty();

	boolean isEmpty();

	void markDirty();

	void prependToGroup(String groupName, IAction action);

	void prependToGroup(String groupName, IContributionItem item);

	IContributionItem remove(String id);

	IContributionItem remove(IContributionItem item);

	void removeAll();

	void update(boolean force);
