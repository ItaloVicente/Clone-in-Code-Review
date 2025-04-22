			if (depth != null) {
				fetch.setDepth(depth.intValue());
			}
			if (shallowSince != null) {
				fetch.setShallowSince(shallowSince);
			}
			for (String shallowExclude : shallowExcludes) {
				fetch.addShallowExclude(shallowExclude);
			}
