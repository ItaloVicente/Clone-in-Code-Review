		if (f == null || repo.getWorkTree() == null)
			throw new IllegalArgumentException();
		if (!f.equals(new File(repo.getWorkTree()
			throw new IllegalArgumentException();
		checkoutEntry(repo
	}

	public static void checkoutEntry(Repository repo
			ObjectReader or) throws IOException {
