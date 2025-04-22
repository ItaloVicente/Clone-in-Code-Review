	private static final ColorDescriptor DEFAULT_COLOR = new RGBColorDescriptor(new RGB(0, 255, 255));

	protected Display display;

	private List<Color> staleColors = new ArrayList<>();

	private Map<String, Color> stringToColor = new HashMap<>(7);
