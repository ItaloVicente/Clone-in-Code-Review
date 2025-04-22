		try {
			ObjectLoader obj = repository.openBlob(indexEntry.getObjectId());
			RawText repositoryContent = new RawText(obj.getBytes());

			File file = resourceEntry.getResource().getLocation().toFile();
			RawText resourceContent = new RawText(file);

			if (resourceContent.size() != repositoryContent.size()) {
				return false;
			} else if (!rawTextMatches(resourceContent, repositoryContent)) {
				return false;
			}

		} catch (IOException e1) {
			e1.printStackTrace();
