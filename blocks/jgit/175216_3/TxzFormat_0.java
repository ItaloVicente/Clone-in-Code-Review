		XZCompressorOutputStream out;
		if (o.containsKey(PRESET)) {
			Integer preset = (Integer) o.get(PRESET);
			out = new XZCompressorOutputStream(s
			o.remove(PRESET);
		} else {
			out = new XZCompressorOutputStream(s);
		}
