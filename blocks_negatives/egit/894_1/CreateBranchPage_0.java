		} else {
			String fullBranch;
			try {
				fullBranch = myRepository.getFullBranch();
				this.branchCombo.setText(fullBranch);
			} catch (IOException e1) {
			}
