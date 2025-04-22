
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (CSSValue cssValue : values) {
			sb.append(cssValue.getCssText() + "\n");
		}
		return sb.toString();
	}

