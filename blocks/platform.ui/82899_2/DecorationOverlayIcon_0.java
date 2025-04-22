	private ImageData getBaseImageData() {
		if (this.baseImageData == null) {
			if (this.baseImage != null) {
				this.baseImageData = this.baseImage.getImageData();
			} else if (this.baseDescriptor != null) {
				this.baseImageData = this.baseDescriptor.getImageData();
			}
		}
		return this.baseImageData;
	}

