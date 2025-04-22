			return;
		}
		try {
			advertisedHaves.addAll(db.getRefDatabase().getAdditionalHaves());
		} catch (IOException ioe) {
		}
