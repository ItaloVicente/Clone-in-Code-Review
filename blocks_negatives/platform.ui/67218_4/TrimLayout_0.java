	/**
	 * @param control
	 *            new window trim to be added
	 * @param areaId
	 *            the area ID
	 * @see #getAreaIds()
	 * @deprecated
	 */
	@Deprecated
	public void addTrim(IWindowTrim control, int areaId) {
		addTrim(areaId, control, null);
	}

	/**
	 *
	 * @param trim
	 *            new window trim to be added
	 * @param areaId
	 *            the area ID
	 * @param beforeMe
	 *            if null, the control will be inserted as the last trim widget
	 *            on this side of the layout. Otherwise, the control will be
	 *            inserted before the given widget.
	 * @see #getAreaIds()
	 * @deprecated
	 */
	@Deprecated
	public void addTrim(IWindowTrim trim, int areaId, IWindowTrim beforeMe) {
		addTrim(areaId, trim, beforeMe);
	}

