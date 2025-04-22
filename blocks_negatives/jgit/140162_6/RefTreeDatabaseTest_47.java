	private void symref(String name, String dst)
			throws IOException {
		commit(new Function() {
			@Override
			public boolean apply(ObjectReader reader, RefTree tree)
					throws IOException {
				Ref old = tree.exactRef(reader, name);
				Command n = new Command(
					old,
					new SymbolicRef(
						name,
						new ObjectIdRef.Unpeeled(Ref.Storage.NEW, dst, null)));
				return tree.apply(Collections.singleton(n));
			}
