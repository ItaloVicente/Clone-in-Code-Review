			IO.readFully(in, path, 0, pathLen);
			md.update(path, 0, pathLen);
		} else {
			final ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			{
				final byte[] buf = new byte[NAME_MASK];
				IO.readFully(in, buf, 0, NAME_MASK);
				tmp.write(buf);
			}
			for (;;) {
				final int c = in.read();
				if (c < 0)
					throw new EOFException(JGitText.get().shortReadOfBlock);
				if (c == 0)
					break;
				tmp.write(c);
