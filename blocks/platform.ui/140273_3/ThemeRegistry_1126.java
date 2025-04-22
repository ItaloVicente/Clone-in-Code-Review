	private List themes;

	private List colors;

	private List fonts;

	private List categories;

	private Map dataMap;

	private Map categoryBindingMap;

	public ThemeRegistry() {
		themes = new ArrayList();
		colors = new ArrayList();
		fonts = new ArrayList();
		categories = new ArrayList();
		dataMap = new HashMap();
		categoryBindingMap = new HashMap();
	}

	void add(IThemeDescriptor desc) {
		if (findTheme(desc.getId()) != null) {
