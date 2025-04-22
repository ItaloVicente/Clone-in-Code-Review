
		if (node.getType() == RepositoryTreeNodeType.ADDITIONALREF) {
			Ref ref = (Ref) node.getObject();
			try {
				RevCommit baseCommit = new RevWalk(node.getRepository())
						.parseCommit(ref.getLeaf().getObjectId());
				new WizardDialog(getShell(event), new CreateBranchWizard(node
						.getRepository(), baseCommit)).open();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			return null;
		}
