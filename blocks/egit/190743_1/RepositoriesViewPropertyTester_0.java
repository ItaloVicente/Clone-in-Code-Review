		if (property.equals("isCommit")) { //$NON-NLS-1$
			if (node instanceof TagNode) {
				return ((TagNode) node).getCommitId() != null;
			}
			return node.getObject() instanceof Ref;
		}
