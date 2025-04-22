	@Override
	protected RevWalk createWalk() {
		RevWalk ret = super.createWalk();
		if (decorate)
			allRefsByPeeledObjectId = getRepository().getAllRefsByPeeledObjectId();
		return ret;
	}

