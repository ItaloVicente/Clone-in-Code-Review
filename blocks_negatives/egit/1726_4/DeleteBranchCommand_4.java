	private void deleteBranch(final RefNode node, final Ref ref)
			throws IOException {
		RefUpdate op = node.getRepository().updateRef(ref.getName());
		op.setRefLogMessage("branch deleted", //$NON-NLS-1$
				false);
		op.setForceUpdate(true);
		op.delete();
