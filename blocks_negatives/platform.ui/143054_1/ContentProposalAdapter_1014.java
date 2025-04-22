					proposalTable.setItemCount(newSize);
					proposalTable.clearAll();
				} else {
					proposalTable.setRedraw(false);
					proposalTable.setItemCount(newSize);
					TableItem[] items = proposalTable.getItems();
					for (int i = 0; i < items.length; i++) {
						TableItem item = items[i];
						IContentProposal proposal = newProposals[i];
						item.setText(getString(proposal));
						item.setImage(getImage(proposal));
						item.setData(proposal);
					}
					proposalTable.setRedraw(true);
				}
