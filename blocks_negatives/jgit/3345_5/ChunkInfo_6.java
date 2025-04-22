		b.append(" [");
		if (getSource() != null)
			b.append(" ").append(getSource());
		if (isFragment())
			b.append(" fragment");
		if (getObjectType() != 0)
			b.append(" ").append(Constants.typeString(getObjectType()));
		if (0 < getObjectsTotal())
			b.append(" objects=").append(getObjectsTotal());
		if (0 < getChunkSizeInBytes())
			b.append(" chunk=").append(getChunkSizeInBytes()).append("B");
		if (0 < getIndexSizeInBytes())
			b.append(" index=").append(getIndexSizeInBytes()).append("B");
		b.append(" ]");
