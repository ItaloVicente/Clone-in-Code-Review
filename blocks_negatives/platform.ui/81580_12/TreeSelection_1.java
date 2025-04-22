	@Override
	public int hashCode() {
		int code = getClass().hashCode();
		if (paths != null) {
			for (TreePath path : paths) {
				code = code * 17 + path.hashCode(getElementComparer());
			}
		}
		return code;
	}

