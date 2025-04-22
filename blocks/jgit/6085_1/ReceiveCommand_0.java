	public void updateType(RevWalk walk) throws IOException {
		if (typeIsCorrect)
			return;
		if (type == Type.UPDATE && !AnyObjectId.equals(oldId
			RevObject o = walk.parseAny(oldId);
			RevObject n = walk.parseAny(newId);
			if (!(o instanceof RevCommit)
					|| !(n instanceof RevCommit)
					|| !walk.isMergedInto((RevCommit) o
				setType(Type.UPDATE_NONFASTFORWARD);
		}
		typeIsCorrect = true;
	}

