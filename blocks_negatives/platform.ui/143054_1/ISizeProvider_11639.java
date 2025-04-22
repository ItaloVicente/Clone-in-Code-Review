	 * Returns the best size for this part, given the available width and height
	 * and the workbench's preferred size for the part. Parts can overload this
	 * to enforce a minimum size, maximum size, or a quantized set of preferred
	 * sizes. If width == true, this method computes a width in pixels. If width
	 * == false, this method computes a height. availableParallel and
	 * availablePerpendicular contain the space available, and preferredParallel
	 * contains the preferred result.
