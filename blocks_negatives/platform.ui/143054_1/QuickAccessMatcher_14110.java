public abstract class QuickAccessElement {


	private static final int[][] EMPTY_INDICES = new int[0][0];
	private QuickAccessProvider provider;

	/**
	 * @param provider
	 */
	public QuickAccessElement(QuickAccessProvider provider) {
		super();
		this.provider = provider;
	}

	/**
	 * Returns the label to be displayed to the user.
	 *
	 * @return the label
	 */
	public abstract String getLabel();

	/**
	 * Returns the image descriptor for this element.
	 *
	 * @return an image descriptor, or null if no image is available
	 */
	public abstract ImageDescriptor getImageDescriptor();
