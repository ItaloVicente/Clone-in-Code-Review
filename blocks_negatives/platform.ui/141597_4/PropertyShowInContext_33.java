		final int prime = 31;
		int result = 1;
		result = prime * result + ((part == null) ? 0 : part.hashCode())
				+ ((getSelection() == null) ? 0 : getSelection().hashCode())
				+ ((getInput() == null) ? 0 : getInput().hashCode());
		return result;
