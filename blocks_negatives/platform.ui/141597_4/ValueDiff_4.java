		final int prime = 31;
		int result = 1;
		Object nv = getNewValue();
		Object ov = getOldValue();
		result = prime * result + ((nv == null) ? 0 : nv.hashCode());
		result = prime * result + ((ov == null) ? 0 : ov.hashCode());
		return result;
