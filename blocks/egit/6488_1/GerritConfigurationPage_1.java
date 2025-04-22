
		branchLabel.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				branch.setFocus();
				branch.selectAll();
			}
		});
		addRefContentProposalToText(branch);
