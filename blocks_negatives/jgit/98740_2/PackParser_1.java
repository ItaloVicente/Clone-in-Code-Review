						JGitText.get().unknownObjectType,
						Integer.valueOf(info.type)));

			ObjectStream cur = readCurs.open(obj, info.type).openStream();
			try {
				long sz = info.size;
				if (cur.getSize() != sz)
					throw new IOException(MessageFormat.format(
							JGitText.get().collisionOn, obj.name()));
				InputStream pck = inflate(Source.DATABASE, sz);
				while (0 < sz) {
					int n = (int) Math.min(readBuffer.length, sz);
					IO.readFully(cur, curBuffer, 0, n);
					IO.readFully(pck, readBuffer, 0, n);
					for (int i = 0; i < n; i++) {
						if (curBuffer[i] != readBuffer[i])
							throw new IOException(MessageFormat.format(JGitText
									.get().collisionOn, obj.name()));
