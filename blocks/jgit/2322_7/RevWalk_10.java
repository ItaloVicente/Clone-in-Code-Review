		if (useGrafts) {
			try {
				setGrafts(repo.getGrafts());
				setReplacements(repo.getReplacements());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			setGrafts(Collections.<AnyObjectId
			setReplacements(Collections.<AnyObjectId
		}
		assert !useGrafts || repo != null;
