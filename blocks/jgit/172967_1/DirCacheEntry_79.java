			md.update((byte) 0);
		} else {
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			tmp.write(previous.path
			pathLen = readNulTerminatedString(in
			path = tmp.toByteArray();
			md.update(path
