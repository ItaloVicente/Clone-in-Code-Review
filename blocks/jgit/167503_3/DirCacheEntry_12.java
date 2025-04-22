			md.update((byte) 0);
		} else {
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			tmp.write(previous.path
			pathLen = 0;
			for (;;) {
				int c = in.read();
				if (c < 0)
					throw new EOFException(JGitText.get().shortReadOfBlock);
				if (c == 0)
					break;
				tmp.write(c);
				pathLen++;
			}
			path = tmp.toByteArray();
			md.update(path
