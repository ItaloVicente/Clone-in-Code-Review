		info = new byte[INFO_LEN];
		infoOffset = 0;
		path = newPath;

		int flags = ((stage & 0x3) << 12);
		if (path.length < NAME_MASK)
			flags |= path.length;
		else
			flags |= NAME_MASK;
		NB.encodeInt16(info, infoOffset + P_FLAGS, flags);
