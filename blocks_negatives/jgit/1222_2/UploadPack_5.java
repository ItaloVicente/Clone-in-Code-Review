			final ObjectId id = ObjectId.fromString(line.substring(5));
			final RevObject o;
			try {
				o = walk.parseAny(id);
			} catch (IOException e) {
				throw new PackProtocolException(MessageFormat.format(JGitText.get().notValid, id.name()), e);
			}
			if (!o.has(ADVERTISED))
				throw new PackProtocolException(MessageFormat.format(JGitText.get().notValid, id.name()));
			try {
				want(o);
			} catch (IOException e) {
				throw new PackProtocolException(MessageFormat.format(JGitText.get().notValid, id.name()), e);
			}
