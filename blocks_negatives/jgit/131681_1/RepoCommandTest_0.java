			String idStr = refName + ":" + path;
			ObjectId id = repo.resolve(idStr);
			if (id == null) {
				throw new RefNotFoundException(
					String.format("repo %s does not have %s", repo.toString(), idStr));
			}
