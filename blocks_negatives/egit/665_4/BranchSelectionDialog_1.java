	private void fillTreeWithBranches(String select) throws IOException {
		String branch = repo.getFullBranch();
		List<String> branches = new ArrayList<String>(repo.getAllRefs()
				.keySet());
		Collections.sort(branches);

		TreeItem curItem = null;
		TreeItem curSubItem = null;
		String curPrefix = null;
		String curSubPrefix = null;
		TreeItem itemToSelect = null;

		for (String ref : branches) {
			String shortName = ref;
			if (ref.startsWith(Constants.R_HEADS)) {
				shortName = ref.substring(11);
				if (!Constants.R_HEADS.equals(curPrefix)) {
					curPrefix = Constants.R_HEADS;
					curSubPrefix = null;
					curSubItem = null;
					curItem = new TreeItem(branchTree, SWT.NONE);
					curItem.setText(UIText.BranchSelectionDialog_LocalBranches);
				}
			} else if (ref.startsWith(Constants.R_REMOTES)) {
				shortName = ref.substring(13);
				if (!Constants.R_REMOTES.equals(curPrefix)) {
					curPrefix = Constants.R_REMOTES;
					curItem = new TreeItem(branchTree, SWT.NONE);
					curItem.setText(UIText.BranchSelectionDialog_RemoteBranches);
					curSubItem = null;
					curSubPrefix = null;
				}

				if (slashPos > -1) {
					String remoteName = shortName.substring(0, slashPos);
					shortName = shortName.substring(slashPos+1);
					if (!remoteName.equals(curSubPrefix)) {
						curSubItem = new TreeItem(curItem, SWT.NONE);
						curSubItem.setText(remoteName);
						curSubPrefix = remoteName;
					}
				} else {
					curSubItem = null;
					curSubPrefix = null;
				}
			} else if (ref.startsWith(Constants.R_TAGS)) {
				shortName = ref.substring(10);
				if (!Constants.R_TAGS.equals(curPrefix)) {
					curPrefix = Constants.R_TAGS;
					curSubPrefix = null;
					curSubItem = null;
					curItem = new TreeItem(branchTree, SWT.NONE);
					curItem.setText(UIText.BranchSelectionDialog_Tags);
				}
			}
			TreeItem item;
			if (curItem == null)
				item = new TreeItem(branchTree, SWT.NONE);
			else if (curSubItem == null)
				item = new TreeItem(curItem, SWT.NONE);
			else item = new TreeItem(curSubItem, SWT.NONE);
			item.setData(ref);
			if (ref.equals(branch)) {
				item.setText(shortName + UIText.BranchSelectionDialog_BranchSuffix_Current);
				FontData fd = item.getFont().getFontData()[0];
				fd.setStyle(fd.getStyle() | SWT.BOLD);
				final Font f = new Font(getShell().getDisplay(), fd);
				item.setFont(f);
				item.addDisposeListener(new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						f.dispose();
					}
				});
				branchTree.showItem(item);
			}
			else item.setText(shortName);
			if (ref.equals(select))
				itemToSelect = item;
			branchTree.setLinesVisible(true);
		}
		if (itemToSelect != null) {
			branchTree.select(itemToSelect);
			branchTree.showItem(itemToSelect);
		}
	}

