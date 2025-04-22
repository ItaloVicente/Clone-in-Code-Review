		if (flags.length != FLAGS_FIELD_LENGTH) {
			flags = new byte[FLAGS_FIELD_LENGTH];
		}
		if (!f.hasFlag(getFlags())) {
			long curFlags = Util.fieldToValue(flags, 0, FLAGS_FIELD_LENGTH);
			Util.valueToFieldOffest(flags, 0, FLAGS_FIELD_LENGTH, curFlags + f.flag);
			encode();
