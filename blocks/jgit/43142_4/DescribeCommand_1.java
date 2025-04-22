	public DescribeCommand setLong(boolean longDesc) {
		this.longDesc = longDesc;
		return this;
	}

	private String longDescription(Ref tag
			throws IOException {
		return String.format(
				"%s-%d-g%s"
				Integer.valueOf(depth)
						.name());
	}

