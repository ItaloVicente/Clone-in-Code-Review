	private static Ref fromData(String name, RefData data) {
		if (data.hasSymref()) {
			Ref leaf = new Unpeeled(NEW, data.getSymref(), null);
			return new SymbolicRef(name, leaf);
		}

		if (!data.hasTarget())
			return new Unpeeled(LOOSE, name, null);
