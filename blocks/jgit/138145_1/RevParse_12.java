	protected void run() {
		try {
			if (all) {
				for (Ref r : db.getRefDatabase().getRefs()) {
					ObjectId objectId = r.getObjectId();
					if (objectId == null) {
						throw new NullPointerException();
					}
					outw.println(objectId.name());
				}
			} else {
				if (verify && commits.size() > 1) {
					final CmdLineParser clp = new CmdLineParser(this);
					throw new CmdLineException(clp
							CLIText.format(CLIText.get().needSingleRevision));
