	Map<String, Ref> getTags() {
		final Map<String, Ref> tags = new HashMap<String, Ref>();
		for (final Ref r : readRefs().values()) {
			if (r.getName().startsWith(R_TAGS))
				tags.put(r.getName().substring(R_TAGS.length()), r);
		}
		return tags;
	}

	private Map<String, Ref> readRefs() {
		final HashMap<String, Ref> avail = new HashMap<String, Ref>();
		readPackedRefs(avail);
		readLooseRefs(avail, REFS_SLASH, refsDir);
		try {
			final Ref r = readRefBasic(Constants.HEAD, 0);
			if (r != null && r.getObjectId() != null)
				avail.put(Constants.HEAD, r);
		} catch (IOException e) {
		}
		db.fireRefsMaybeChanged();
		return avail;
	}

	private synchronized void readPackedRefs(final Map<String, Ref> avail) {
		refreshPackedRefs();
		avail.putAll(packedRefs);
	}

	private void readLooseRefs(final Map<String, Ref> avail,
			final String prefix, final File dir) {
		final File[] entries = dir.listFiles();
		if (entries == null)
			return;

		for (final File ent : entries) {
			final String entName = ent.getName();
			if (".".equals(entName) || "..".equals(entName))
				continue;
			if (ent.isDirectory()) {
				readLooseRefs(avail, prefix + entName + "/", ent);
			} else {
				try {
					final Ref ref = readRefBasic(prefix + entName, 0);
					if (ref != null)
						avail.put(ref.getOrigName(), ref);
				} catch (IOException e) {
					continue;
				}
			}
		}
	}

	Ref peel(final Ref ref) {
		if (ref.isPeeled())
			return ref;
		ObjectId peeled = null;
		try {
			Object target = db.mapObject(ref.getObjectId(), ref.getName());
			while (target instanceof Tag) {
				final Tag tag = (Tag)target;
				peeled = tag.getObjId();
				if (Constants.TYPE_TAG.equals(tag.getType()))
					target = db.mapObject(tag.getObjId(), ref.getName());
				else
					break;
			}
		} catch (IOException e) {
		}
		return new Ref(ref.getStorage(), ref.getName(), ref.getObjectId(), peeled, true);

	}

	private File fileForRef(final String name) {
		if (name.startsWith(REFS_SLASH))
			return new File(refsDir, name.substring(REFS_SLASH.length()));
		return new File(gitDir, name);
	}

	private Ref readRefBasic(final String name, final int depth) throws IOException {
		return readRefBasic(name, name, depth);
	}

	private synchronized Ref readRefBasic(final String origName,
			final String name, final int depth) throws IOException {
		Ref ref = looseRefs.get(origName);
		final File loose = fileForRef(name);
		final long mtime = loose.lastModified();
		if (ref != null) {
			Long cachedlastModified = looseRefsMTime.get(name);
			if (cachedlastModified != null && cachedlastModified == mtime) {
				if (packedRefs.containsKey(origName))
					return new Ref(Storage.LOOSE_PACKED, origName, ref
							.getObjectId(), ref.getPeeledObjectId(), ref
							.isPeeled());
				else
					return ref;
			}
			looseRefs.remove(origName);
			looseRefsMTime.remove(origName);
		}

		if (mtime == 0) {
			ref = packedRefs.get(name);
			if (ref != null)
				if (!ref.getOrigName().equals(origName))
					ref = new Ref(Storage.LOOSE_PACKED, origName, name, ref.getObjectId());
			return ref;
		}

		String line = null;
		try {
			Long cachedlastModified = looseRefsMTime.get(name);
			if (cachedlastModified != null && cachedlastModified == mtime) {
				line = looseSymRefs.get(name);
			}
			if (line == null) {
				line = readLine(loose);
				looseRefsMTime.put(name, mtime);
				looseSymRefs.put(name, line);
			}
		} catch (FileNotFoundException notLoose) {
			return packedRefs.get(name);
		}

		if (line == null || line.length() == 0) {
			looseRefs.remove(origName);
			looseRefsMTime.remove(origName);
			return new Ref(Ref.Storage.LOOSE, origName, name, null);
		}

		if (line.startsWith("ref: ")) {
			if (depth >= 5) {
				throw new IOException("Exceeded maximum ref depth of " + depth
						+ " at " + name + ".  Circular reference?");
			}

			final String target = line.substring("ref: ".length());
			Ref r = readRefBasic(target, target, depth + 1);
			Long cachedMtime = looseRefsMTime.get(name);
			if (cachedMtime != null && cachedMtime != mtime)
				setModified();
			looseRefsMTime.put(name, mtime);
			if (r == null)
				return new Ref(Ref.Storage.LOOSE, origName, target, null);
			if (!origName.equals(r.getName()))
				r = new Ref(Ref.Storage.LOOSE_PACKED, origName, r.getName(), r.getObjectId(), r.getPeeledObjectId(), true);
			return r;
		}

		setModified();

		final ObjectId id;
		try {
			id = ObjectId.fromString(line);
		} catch (IllegalArgumentException notRef) {
			throw new IOException("Not a ref: " + name + ": " + line);
		}

		Storage storage;
		if (packedRefs.containsKey(name))
			storage = Ref.Storage.LOOSE_PACKED;
		else
			storage = Ref.Storage.LOOSE;
		ref = new Ref(storage, name, id);
		looseRefs.put(name, ref);
		looseRefsMTime.put(name, mtime);

		if (!origName.equals(name)) {
			ref = new Ref(Ref.Storage.LOOSE, origName, name, id);
			looseRefs.put(origName, ref);
		}

		return ref;
	}

	private synchronized void refreshPackedRefs() {
		final long currTime = packedRefsFile.lastModified();
		final long currLen = currTime == 0 ? 0 : packedRefsFile.length();
		if (currTime == packedRefsLastModified && currLen == packedRefsLength)
			return;
		if (currTime == 0) {
			packedRefsLastModified = 0;
			packedRefsLength = 0;
			packedRefs = new HashMap<String, Ref>();
			return;
		}

		final Map<String, Ref> newPackedRefs = new HashMap<String, Ref>();
		try {
			final BufferedReader b = openReader(packedRefsFile);
			try {
				String p;
				Ref last = null;
				while ((p = b.readLine()) != null) {
					if (p.charAt(0) == '#')
						continue;

					if (p.charAt(0) == '^') {
						if (last == null)
							throw new IOException("Peeled line before ref.");

						final ObjectId id = ObjectId.fromString(p.substring(1));
						last = new Ref(Ref.Storage.PACKED, last.getName(), last
								.getName(), last.getObjectId(), id, true);
						newPackedRefs.put(last.getName(), last);
						continue;
					}

					final int sp = p.indexOf(' ');
					final ObjectId id = ObjectId.fromString(p.substring(0, sp));
					final String name = copy(p, sp + 1, p.length());
					last = new Ref(Ref.Storage.PACKED, name, name, id);
					newPackedRefs.put(last.getName(), last);
				}
			} finally {
				b.close();
			}
			packedRefsLastModified = currTime;
			packedRefsLength = currLen;
			packedRefs = newPackedRefs;
			setModified();
		} catch (FileNotFoundException noPackedRefs) {
			packedRefsLastModified = 0;
			packedRefsLength = 0;
			packedRefs = newPackedRefs;
		} catch (IOException e) {
			throw new RuntimeException("Cannot read packed refs", e);
		}
	}

	private static String copy(final String src, final int off, final int end) {
		return new StringBuilder(end - off).append(src, off, end).toString();
	}

	private void lockAndWriteFile(File file, byte[] content) throws IOException {
		String name = file.getName();
		final LockFile lck = new LockFile(file);
		if (!lck.lock())
			throw new ObjectWritingException("Unable to lock " + name);
		try {
			lck.write(content);
		} catch (IOException ioe) {
			throw new ObjectWritingException("Unable to write " + name, ioe);
		}
		if (!lck.commit())
			throw new ObjectWritingException("Unable to write " + name);
	}

	synchronized void removePackedRef(String name) throws IOException {
		packedRefs.remove(name);
		writePackedRefs();
	}

	private void writePackedRefs() throws IOException {
		new RefWriter(packedRefs.values()) {
			@Override
			protected void writeFile(String name, byte[] content) throws IOException {
				lockAndWriteFile(new File(db.getDirectory(), name), content);
			}
		}.writePackedRefs();
	}

	private static String readLine(final File file)
			throws FileNotFoundException, IOException {
		final byte[] buf = IO.readFully(file, 4096);
		int n = buf.length;

		while (n > 0 && Character.isWhitespace(buf[n - 1]))
			n--;

		if (n == 0)
			return null;
		return RawParseUtils.decode(buf, 0, n);
	}

	private static BufferedReader openReader(final File fileLocation)
			throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(
				fileLocation), Constants.CHARSET));
	}
