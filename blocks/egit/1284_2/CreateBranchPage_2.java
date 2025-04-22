		if (commitMode) {
			this.branchCombo.add(myBaseCommit.name());
			this.branchCombo.setText(myBaseCommit.name());
			this.branchCombo.setEnabled(false);
		} else {
			try {
				for (Entry<String, Ref> ref : myRepository.getRefDatabase()
						.getRefs(Constants.R_HEADS).entrySet()) {
					if (!ref.getValue().isSymbolic())
						this.branchCombo.add(ref.getValue().getName());
				}
				for (Entry<String, Ref> ref : myRepository.getRefDatabase()
						.getRefs(Constants.R_REMOTES).entrySet()) {
					if (!ref.getValue().isSymbolic())
						this.branchCombo.add(ref.getValue().getName());
				}
				for (Entry<String, Ref> ref : myRepository.getRefDatabase()
						.getRefs(Constants.R_TAGS).entrySet()) {
					if (!ref.getValue().isSymbolic())
						this.branchCombo.add(ref.getValue().getName());
				}

			} catch (IOException e1) {
