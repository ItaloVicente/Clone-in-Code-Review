		return gsds.contains(res.getProject()) && !isIgnoredHint(res);
	}


	@Override
	public IResource[] members(IResource res) throws TeamException {
		if(res.getType() == IResource.FILE) {
			return new IResource[0];
		}

