	private boolean onlyBranchesAreSelected(TreeSelection selection) {
		Iterator selIterator = selection.iterator();
		while (selIterator.hasNext()) {
			Object sel = selIterator.next();
			if (sel instanceof RefNode) {
				RefNode node = (RefNode) sel;
				String refName = node.getObject().getName();
				if (!refName.startsWith(Constants.R_HEADS))
					return false;
			} else
				return false;
		}

		return true;
	}

