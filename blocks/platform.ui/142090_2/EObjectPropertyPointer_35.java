		if (getImmediateNode() == null) {
			super.createPath(context);
			baseValue = UNINITIALIZED;
			value = UNINITIALIZED;
		}
		return this;
	}
