		if (type == Constants.OBJ_BAD) {
			if (!(base instanceof LargePackedDeltaObject))
				type = base.getType();
		}
		if (size == SIZE_UNKNOWN)
			size = ds.getSize();
		return ds;
