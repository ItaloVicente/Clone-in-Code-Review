
		if (r.getPeeledObjectId() != null)
			tagTargets.add(r.getPeeledObjectId());

		else if (r.getObjectId() != null
				&& r.getName().startsWith(Constants.R_HEADS))
			tagTargets.add(r.getObjectId());
