		useJVMDefaultInternal();
	}

	private static NLS useJVMDefaultInternal() {
		NLS b = new NLS(Locale.getDefault());
		local.set(b);
		return b;
