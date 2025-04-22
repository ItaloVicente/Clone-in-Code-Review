	enum ProtocolVersion {
		V0("0")
		V2("2");

		final String name;

		ProtocolVersion(String name) {
			this.name = name;
		}

		static @Nullable ProtocolVersion parse(@Nullable String name) {
			if (name == null) {
				return null;
			}
			for (ProtocolVersion v : ProtocolVersion.values()) {
				if (v.name.equals(name)) {
					return v;
				}
			}
			return null;
		}
	}

