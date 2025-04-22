	public void writeSideband(int sideband
		if (!useSideband) {
			return;
		}
		byte[] buf = Constants.encode(s);
		formatLength(buf.length + 5);
		out.write(lenbuffer
		out.write(sideband);
		out.write(buf
	}

