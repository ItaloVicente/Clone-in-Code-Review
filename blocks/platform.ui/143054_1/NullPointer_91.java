		if (parent != null) {
			return parent.createPath(context, value).getValuePointer();
		}
		throw new UnsupportedOperationException(
			"Cannot create the root object: " + asPath());
	}

	@Override
