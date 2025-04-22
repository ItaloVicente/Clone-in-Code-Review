	@Override
	public void setAdvertisedRefs(final Map<String
		super.setAdvertisedRefs(allRefs
		final Map<String
		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
			final RefDatabase bootstrap = ((RefTreeDatabase) getRepository().getRefDatabase()).getBootstrap();
			try {
				for (Map.Entry<String
					refs.put(entry.getKey()
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
