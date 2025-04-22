			b.append(" time=").append(new Date(time));
		b.append(" type=").append(Constants.typeString(typeCode));
		b.append(" offset=").append(offset);
		b.append(" packedSize=").append(packedSize);
		b.append(" inflatedSize=").append(inflatedSize);
		if (deltaBase != null)
			b.append(" deltaBase=").append(deltaBase.name());
		if (fragmented)
			b.append(" fragmented");
		b.append(" ]");
