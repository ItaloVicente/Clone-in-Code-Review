		try (TestRepository src = createTestRepository()) {
			final RevBlob Q_txt = src.blob("new text");
			final RevCommit Q = src.commit().add("Q"
			final Repository db = src.getRepository();
			final String dstName = Constants.R_HEADS + "new.branch";
			PushResult result;

			OutputStream out = new ByteArrayOutputStream();
			try (Transport t = Transport.open(db
				final String srcExpr = Q.name();
				final boolean forceUpdate = false;
				final String localName = null;
				final ObjectId oldId = null;

				RemoteRefUpdate update = new RemoteRefUpdate(
						src.getRepository()
						localName
				result = t.push(NullProgressMonitor.INSTANCE
						Collections.singleton(update)
			}
