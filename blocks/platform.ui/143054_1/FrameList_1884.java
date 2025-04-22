	private int current;

	public FrameList(IFrameSource source) {
		this.source = source;
		Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME, 0);
		frame.setParent(this);
		frame.setIndex(0);
