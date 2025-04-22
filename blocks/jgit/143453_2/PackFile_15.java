
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "PackFile [packFileName=" + packFile.getName() + "
				+ packFile.length() + "
				+ ObjectId.fromRaw(packChecksum).name() + "]";
	}
