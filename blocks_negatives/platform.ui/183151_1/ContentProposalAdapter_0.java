	/**
	 * Return the content adapter that can get or retrieve the text contents
	 * from the adapter's control. This method is used when a client, such as a
	 * content proposal listener, needs to update the control's contents
	 * manually.
	 *
	 * @return the {@link IControlContentAdapter} which can update the control
	 *         text.
	 */
	public IControlContentAdapter getControlContentAdapter() {
		return controlContentAdapter;
	}

