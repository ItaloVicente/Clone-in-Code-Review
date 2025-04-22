	boolean isInClone() throws IOException {
		return hasDanglingHead() && !packedRefsFile.exists() && !hasLooseRef();
	}

	private boolean hasDanglingHead() throws IOException {
		Ref head = exactRef(Constants.HEAD);
		if (head != null) {
			ObjectId id = head.getObjectId();
			return id == null || id.equals(ObjectId.zeroId());
		}
		return false;
	}

	private boolean hasLooseRef() throws IOException {
		return Files.walk(refsDir.toPath()).filter(p -> Files.isRegularFile(p))
				.findAny().isPresent();
	}

