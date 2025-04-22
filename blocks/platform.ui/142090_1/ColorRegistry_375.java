	protected Display display;

	private List<Color> staleColors = new ArrayList<>();

	private Map<String, Color> stringToColor = new HashMap<>(7);

	private Map<String, RGB> stringToRGB = new HashMap<>(7);

	protected Runnable displayRunnable = this::clearCaches;
