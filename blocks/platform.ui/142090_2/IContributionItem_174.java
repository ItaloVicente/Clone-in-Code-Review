	void dispose();

	void fill(Composite parent);

	void fill(Menu parent, int index);

	void fill(ToolBar parent, int index);

	void fill(CoolBar parent, int index);

	String getId();

	boolean isEnabled();

	boolean isDirty();

	boolean isDynamic();

	boolean isGroupMarker();

	boolean isSeparator();

	boolean isVisible();

	void saveWidgetState();

	void setParent(IContributionManager parent);

	void setVisible(boolean visible);

	void update();

	void update(String id);
