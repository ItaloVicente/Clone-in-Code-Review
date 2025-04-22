
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TrackingRefUpdate[");
		sb.append(remoteName);
		sb.append(" -> ");
		sb.append(localName);
		if (forceUpdate)
			sb.append(" (forced)");
		sb.append(" ");
		sb.append(oldObjectId == null ? "" : oldObjectId.abbreviate(7).name());
		sb.append("..");
		sb.append(newObjectId == null ? "" : newObjectId.abbreviate(7).name());
		sb.append("]");
		return sb.toString();
	}
