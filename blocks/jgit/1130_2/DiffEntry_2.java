
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("DiffEntry[");
		buf.append(changeType);
		buf.append(" ");
		switch (changeType) {
		case ADD:
			buf.append(newName);
			break;
		case COPY:
			buf.append(oldName + "->" + newName);
			break;
		case DELETE:
			buf.append(oldName);
			break;
		case MODIFY:
			buf.append(oldName);
			break;
		case RENAME:
			buf.append(oldName + "->" + newName);
			break;
		}
		buf.append("]");
		return buf.toString();
	}
