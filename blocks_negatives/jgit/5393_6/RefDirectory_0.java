		final ObjectId oldId = update.getOldObjectId();
		final ObjectId newId = update.getNewObjectId();
		final Ref ref = update.getRef();

		PersonIdent ident = update.getRefLogIdent();
		if (ident == null)
			ident = new PersonIdent(parent);
		else
			ident = new PersonIdent(ident);

		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(oldId));
		r.append(' ');
		r.append(ObjectId.toString(newId));
		r.append(' ');
		r.append(ident.toExternalString());
		r.append('\t');
		r.append(msg);
		r.append('\n');
		final byte[] rec = encode(r.toString());

		if (deref && ref.isSymbolic()) {
			log(ref.getName(), rec);
			log(ref.getLeaf().getName(), rec);
		} else {
			log(ref.getName(), rec);
		}
	}

	private void log(final String refName, final byte[] rec) throws IOException {
		final File log = logFor(refName);
		final boolean write;
		if (isLogAllRefUpdates() && shouldAutoCreateLog(refName))
			write = true;
		else if (log.isFile())
			write = true;
		else
			write = false;

		if (write) {
			WriteConfig wc = getRepository().getConfig().get(WriteConfig.KEY);
			FileOutputStream out;
			try {
				out = new FileOutputStream(log, true);
			} catch (FileNotFoundException err) {
				final File dir = log.getParentFile();
				if (dir.exists())
					throw err;
				if (!dir.mkdirs() && !dir.isDirectory())
					throw new IOException(MessageFormat.format(JGitText.get().cannotCreateDirectory, dir));
				out = new FileOutputStream(log, true);
			}
			try {
				if (wc.getFSyncRefFiles()) {
					FileChannel fc = out.getChannel();
					ByteBuffer buf = ByteBuffer.wrap(rec);
					while (0 < buf.remaining())
						fc.write(buf);
					fc.force(true);
				} else {
					out.write(rec);
				}
			} finally {
				out.close();
			}
		}
	}

	private boolean isLogAllRefUpdates() {
		return parent.getConfig().get(CoreConfig.KEY).isLogAllRefUpdates();
	}

	private boolean shouldAutoCreateLog(final String refName) {
				|| refName.equals(R_STASH);
