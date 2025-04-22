			IO.readFully(in, path, 0, pathLen);
			md.update(path, 0, pathLen);
		} else {
			final ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			{
				final byte[] buf = new byte[NAME_MASK];
				IO.readFully(in, buf, 0, NAME_MASK);
				tmp.write(buf);
