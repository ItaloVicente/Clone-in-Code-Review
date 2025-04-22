	public static enum Replace {
		INDEX,

		HEAD
	}

	private Replace replace;

	public DiscardChangesActionHandler(Replace replaceType) {
		this.replace = replaceType;
	}

