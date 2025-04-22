		if (depth != null) {
			command.setDepth(depth.intValue());
		}
		if (shallowSince != null) {
			command.setShallowSince(shallowSince);
		}
		for (String shallowExclude : shallowExcludes) {
			command.addShallowExclude(shallowExclude);
		}

