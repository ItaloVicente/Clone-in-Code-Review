	public Boolean getAsText() {
	    return this.asText;
	}

	public void setAsText(boolean asText) {
	    if (asText && asBinary) {
		throw new IllegalArgumentException(
		        JGitText.get().cannotUseBothOptions);
	    }
	    this.asText = asText;
	}

	public Boolean getAsBinary() {
	    return this.asBinary;
	}

	public void setAsBinary(boolean asBinary) {
	    if (asText && asBinary) {
		throw new IllegalArgumentException(
			JGitText.get().cannotUseBothOptions);
	    }
	    this.asBinary = asBinary;
	}

