			outw.println(s);
		}
		byte[] rawSignature = tag.getRawGpgSignature();
		if (rawSignature != null) {
			for (String s : lines) {
				outw.println(s);
			}
