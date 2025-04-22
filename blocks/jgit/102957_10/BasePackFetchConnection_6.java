		final FileBasedShallow shallow = new FileBasedShallow(this.local);
		final List<ObjectId> shallowCommits = shallow.read();
		for (ObjectId shallowCommit : shallowCommits) {
			final String id = shallowCommit.getName();
			final StringBuilder builder = new StringBuilder(46);
			builder.append(id);
			builder.append('\n');
			p.writeString(builder.toString());
		}

		if (Depth.isSet(depth)) {
			final StringBuilder builder = new StringBuilder(46);
			builder.append(depth.getDepth());
			builder.append('\n');
			p.writeString(builder.toString());
		}
