	private void update(String name, ObjectId id)
			throws IOException {
		commit(new Function() {
			@Override
			public boolean apply(ObjectReader reader, RefTree tree)
					throws IOException {
				Ref old = tree.exactRef(reader, name);
				Command n;
				try (RevWalk rw = new RevWalk(repo)) {
					n = new Command(old,
							Command.toRef(rw, id, null, name, true));
				}
				return tree.apply(Collections.singleton(n));
