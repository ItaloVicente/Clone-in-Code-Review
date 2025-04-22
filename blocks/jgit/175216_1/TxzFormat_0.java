		XZCompressorOutputStream out;
		if (o.containsKey(PRESET)) {
			out = new XZCompressorOutputStream(s
			o.remove(PRESET);
		} else {
			out = new XZCompressorOutputStream(s);
		}
