	/**
	 * A file for customizing default preference values for a product. The value is
	 * interpreted as either a URL or a bundle-relative path by the runtime. This is
	 * not referenced from the workbench.
	 * <p>
	 * The contents must be the same format as a {@link java.util.Properties} file
	 * with the key/value pairs being:
	 *
	 * <pre>
	 * qualifier/key=value
	 * </pre>
	 *
	 * Where <code>qualifier</code> is typically the bundle id.
	 * </p>
	 */

	/**
	 * An image to be used as the window icon for this product (16x16). Products
	 * designed to run "headless" typically would not have such an image.
	 * <p>
	 * The value is either a fully qualified valid URL or a path relative to the
	 * product's defining bundle.
	 * </p>
	 * <p>
	 * If the <code>WINDOW_IMAGES</code> property is given, then it supercedes this
	 * one.
	 * </p>
	 *
	 * @deprecated use WINDOW_IMAGES instead (see recommendations there)
	 */
	@Deprecated

