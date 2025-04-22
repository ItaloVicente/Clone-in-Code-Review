			if (!ref.isSymbolic()) {

				if (!isBare) {
					MenuItem checkout = new MenuItem(men, SWT.PUSH);
					checkout.setText(UIText.RepositoriesView_CheckOut_MenuItem);

					checkout.setEnabled(!isRefCheckedOut(node.getRepository(),
							ref.getName()));

					checkout.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							checkoutBranch(node, ref.getLeaf().getName());
						}
					});

					new MenuItem(men, SWT.SEPARATOR);
				}
