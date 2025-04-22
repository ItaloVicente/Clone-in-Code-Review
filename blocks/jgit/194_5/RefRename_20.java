	protected boolean needToUpdateHEAD() throws IOException {
		Ref head = source.getRefDatabase().getRef(Constants.HEAD);
		if (head.isSymbolic()) {
			head = head.getTarget();
			return head.getName().equals(source.getName());
		}
		return false;
