	private boolean onlyBranchesAreSelected(TreeSelection selection) {
		Iterator selIterator = selection.iterator();
		for (Object sel = selIterator.next(); selIterator.hasNext(); sel = selIterator
				.next()) {
			if (sel instanceof RefNode) {
				RefNode node = (RefNode) sel;
				String refName = node.getObject().getName();
				if (!refName.startsWith(Constants.R_HEADS))
					return false;

			}
		}

		return true;
	}

