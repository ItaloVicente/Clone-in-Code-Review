
	private void show(Ref ref
			throws IOException {
		outw.print("ref: ");
		outw.print(ref.getName());
		outw.print('\t');
		outw.print(name);
		outw.println();
	}
