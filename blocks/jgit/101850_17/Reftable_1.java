	@Nullable
	public Ref resolve(Ref symref) throws IOException {
		return resolve(symref
	}

	private Ref resolve(Ref ref
		if (!ref.isSymbolic()) {
			return ref;
		}

		Ref dst = ref.getTarget();
		if (MAX_SYMBOLIC_REF_DEPTH <= depth) {
		}

		dst = exactRef(dst.getName());
		if (dst == null) {
			return ref;
		}

		dst = resolve(dst
		if (dst == null) {
		}
		return new SymbolicRef(ref.getName()
	}

