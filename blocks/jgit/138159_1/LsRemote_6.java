		try {
			refs.addAll(command.call());
			for (Ref r : refs) {
				show(r.getObjectId()
				if (r.getPeeledObjectId() != null) {
					show(r.getPeeledObjectId()
				}
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
