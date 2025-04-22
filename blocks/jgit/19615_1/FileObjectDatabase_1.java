	@Override
	public boolean equals(Object o) {
		return o instanceof FileObjectDatabase
				&& ((FileObjectDatabase)o).getDirectory().equals(this.getDirectory());
  }

	@Override
	public int hashCode() {
		return this.getDirectory().hashCode();
	}

