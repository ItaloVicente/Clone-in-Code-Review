	private void advertiseTag(final RevTag tag, final String refName)
			throws IOException {
		RevObject o = tag;
		do {
			final RevObject target = ((RevTag) o).getObject();
			try {
				walk.parseHeaders(target);
			} catch (IOException err) {
				return;
			}
			target.add(ADVERTISED);
			o = target;
		} while (o instanceof RevTag);
		advertiseAny(tag.getObject(), refName);
	}

