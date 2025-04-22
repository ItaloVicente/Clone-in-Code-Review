		final RepositoryShallow shallow = this.local
				.getRepositoryShallowHandler();
		shallow.lock();
		final List<ObjectId> shallowCommits = shallow.read();
		shallow.unlock(false);
		for (ObjectId shallowCommit : shallowCommits) {
			final String id = shallowCommit.getName();
			final StringBuilder builder = new StringBuilder(46);
			builder.append(id);
			builder.append('\n');
			p.writeString(builder.toString());
		}

		if (Depth.isSet(depth) && sendWantsDeepen) {
			final StringBuilder builder = new StringBuilder(46);
			builder.append(depth.getDepth());
			builder.append('\n');
			p.writeString(builder.toString());
		}
