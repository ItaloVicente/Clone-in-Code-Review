		if (rc.getRawBuffer() == null)
			try {
				new RevWalk(db).parseBody(rc);
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
