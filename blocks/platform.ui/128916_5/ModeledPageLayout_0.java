	@Override
	public void addView(String viewId, int relationship, float ratio, String refId, boolean minimized,
			boolean visible) {
		if (minimized) {
			E4Util.unsupported("addView: use of minimized for " + viewId + " ref " + refId); //$NON-NLS-1$ //$NON-NLS-2$
		}
		insertView(viewId, relationship, ratio, refId, visible, true);
	}

