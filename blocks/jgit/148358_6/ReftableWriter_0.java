	public ReftableWriter begin(OutputStream os) {
		if (outputStream != null) {
		}
		outputStream = os;
		return begin();
	}

	public ReftableWriter begin() {
		if (out != null) {
		}

