		this(document, document.getLength(), -1);
	}

	public DiffStyleRangeFormatter(IDocument document, int offset,
			int maxLines) {
		super(new DocumentOutputStream(document, offset));
		this.stream = (DocumentOutputStream) getOutputStream();
		this.maxLines = maxLines;
