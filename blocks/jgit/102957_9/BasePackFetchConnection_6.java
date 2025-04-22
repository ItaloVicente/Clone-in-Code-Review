
		if (Depth.isSet(depth)) {
			final StringBuilder builder = new StringBuilder(46);
			builder.append(depth.getDepth());
			builder.append('\n');
			p.writeString(builder.toString());
		}
