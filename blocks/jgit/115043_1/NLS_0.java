		NLS b = local.get();
		if (b == null) {
			b = new NLS(Locale.getDefault());
			local.set(b);
		}
		return b.get(type);
