	 * @param width
	 *            indicates whether a width (if <code>true</code>) or a height
	 *            (if <code>false</code>) is being computed
	 * @param availableParallel
	 *            available space. This is a width (pixels) if width == true,
	 *            and a height (pixels) if width == false. A return value larger
	 *            than this will be ignored.
	 * @param availablePerpendicular
	 *            available space perpendicular to the direction being measured
	 *            or INFINITE if unbounded (pixels). This is a height if width
	 *            == true, or a height if width == false. Implementations will
	 *            generally ignore this argument unless they contain wrapping
	 *            widgets. Note this argument will only contain meaningful
	 *            information if the part returns the SWT.WRAP flag from
	 *            getSizeFlags(width)
	 * @param preferredResult
	 *            preferred size of the control (pixels, <= availableParallel).
	 *            Set to INFINITE if unknown or unbounded.
	 * @return returns the preferred size of the control (pixels). This is a
	 *         width if width == true or a height if width == false. Callers are
	 *         responsible for rounding down the return value if it is larger
	 *         than availableParallel. If availableParallel is INFINITE, then a
	 *         return value of INFINITE is permitted, indicating that the
	 *         preferred size of the control is unbounded.
