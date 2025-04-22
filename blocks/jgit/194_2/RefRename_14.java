	protected boolean needToUpdateHEAD() throws IOException {
		Ref head = source.getRefDatabase().getRef(Constants.HEAD);
		if (head instanceof SymbolicRef) {
			head = ((SymbolicRef) head).getTarget();
			return head.getName().equals(source.getName());
		}
		return false;
