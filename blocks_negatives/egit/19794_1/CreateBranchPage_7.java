		if (this.myBaseCommit != null) {
			this.branchCombo.add(myBaseCommit.name());
			this.branchCombo.setText(myBaseCommit.name());
			try {
				Map<String, Ref> map = myRepository.getRefDatabase().getRefs(
						Constants.R_HEADS);
				for (Entry<String, Ref> entry : map.entrySet()) {
					if (entry.getValue().getLeaf().getObjectId()
							.equals(myBaseCommit))
						this.branchCombo.add(entry.getValue().getName());
				}
				map = myRepository.getRefDatabase()
						.getRefs(Constants.R_REMOTES);
				String firstRemote = null;
				for (Entry<String, Ref> entry : map.entrySet()) {
					if (entry.getValue().getLeaf().getObjectId()
							.equals(myBaseCommit)) {
						this.branchCombo.add(entry.getValue().getName());
						if (firstRemote == null)
							firstRemote = entry.getValue().getName();
					}
				}
				if (firstRemote != null) {
					this.branchCombo.setText(firstRemote);
					suggestBranchName(firstRemote);
				}
			} catch (IOException e) {
				Activator.logError(
						"Exception while trying to find Refs for Commit", e); //$NON-NLS-1$
			}
			this.branchCombo.setEnabled(this.branchCombo.getItemCount() > 1);
		} else {
			List<String> refs = new ArrayList<String>();
			RefDatabase refDatabase = myRepository.getRefDatabase();
			try {
				for (Ref ref : refDatabase.getAdditionalRefs())
					refs.add(ref.getName());

				Set<Entry<String, Ref>> entrys = refDatabase.getRefs(
						RefDatabase.ALL).entrySet();
				for (Entry<String, Ref> ref : entrys)
					refs.add(ref.getValue().getName());
			} catch (IOException e1) {
			}

			Collections.sort(refs, CommonUtils.STRING_ASCENDING_COMPARATOR);
			for (String refName : refs)
				this.branchCombo.add(refName);

			if (myBaseRef != null)
				this.branchCombo.setText(myBaseRef);
		}

		this.branchCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ref = branchCombo.getText();
				suggestBranchName(ref);
				upstreamConfig = UpstreamConfig.getDefault(myRepository, ref);
				checkPage();
			}
		});

