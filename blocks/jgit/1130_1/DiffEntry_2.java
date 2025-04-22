
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("DiffEntry");
		switch (changeType) {
		case ADD:
			buf.append(" (Add " + newName + ")");
			break;
		case COPY:
			buf.append(" (Copy " + oldName + " to " + newName + ")");
			break;
		case DELETE:
			buf.append(" (Delete " + oldName + ")");
			break;
		case MODIFY:
			buf.append(" (Modify " + oldName + ")");
			break;
		case RENAME:
			buf.append(" (Rename " + oldName + " to " + newName + ")");
			break;
		}
		return buf.toString();
	}
