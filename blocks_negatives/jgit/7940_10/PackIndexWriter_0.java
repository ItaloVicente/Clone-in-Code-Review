		int version = 1;
		LOOP: for (final PackedObjectInfo oe : objs) {
			switch (version) {
			case 1:
				if (PackIndexWriterV1.canStore(oe))
					continue;
				version = 2;
			case 2:
				break LOOP;
			}
