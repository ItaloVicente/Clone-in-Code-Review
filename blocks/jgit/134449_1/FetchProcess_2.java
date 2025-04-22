		String want = spec.getSource();
		if (ObjectId.isId(want)) {
			want(ObjectId.fromString(want));
			return;
		}

		Ref src = conn.getRef(want);
