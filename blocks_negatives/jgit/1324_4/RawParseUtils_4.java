		final MutableInteger ptrout = new MutableInteger();
		final long when = parseLongBase10(raw, emailE + 1, ptrout);
		final int whenE = ptrout.value;
			raw[whenE] == '\n') {
			return null;
		}

		final int tz = parseTimeZoneOffset(raw, whenE);
