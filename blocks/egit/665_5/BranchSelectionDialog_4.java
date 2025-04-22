		branchTree.addOpenListener(new IOpenListener() {

			public void open(OpenEvent event) {
				RepositoryTreeNode node = (RepositoryTreeNode) ((IStructuredSelection) branchTree
						.getSelection()).getFirstElement();
				if (node.getType() != RepositoryTreeNodeType.REF)
					branchTree.setExpandedState(node, !branchTree
							.getExpandedState(node));
				else if (confirmationBtn.isEnabled())
					okPressed();

			}
		});

