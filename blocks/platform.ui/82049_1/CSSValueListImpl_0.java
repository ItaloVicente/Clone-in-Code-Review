
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CSSValueListImpl:\n");
		for (CSSValue cssValue : values) {
			sb.append(cssValue.getCssText() + "\n");
		}
		return sb.toString();
	}

