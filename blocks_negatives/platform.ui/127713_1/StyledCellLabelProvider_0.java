	 * Specifies whether owner draw rendering is enabled for this label
	 * provider. By default owner draw rendering is enabled. If owner draw
	 * rendering is disabled, rendering is done by the viewer and no styled
	 * ranges (see {@link ViewerCell#getStyleRanges()}) are drawn.
	 * It is the caller's responsibility to also call
	 * {@link StructuredViewer#refresh()} or similar methods to update the
	 * underlying widget.
