		if (node.getType() == RepositoryTreeNodeType.TAG) {

			final Ref ref = (Ref) node.getObject();

			MenuItem checkout = new MenuItem(men, SWT.PUSH);
			checkout.setText(UIText.RepositoriesView_CheckOut_MenuItem);

			checkout.setEnabled(!isRefCheckedOut(node.getRepository(), ref
					.getName()));

			checkout.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					checkoutBranch(node, ref.getLeaf().getName());
				}
			});

		}

