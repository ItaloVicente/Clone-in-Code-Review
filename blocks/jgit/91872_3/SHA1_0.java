	public SHA1 reset() {
		h.init();
		length = 0;
		foundCollision = false;
		return this;
	}

