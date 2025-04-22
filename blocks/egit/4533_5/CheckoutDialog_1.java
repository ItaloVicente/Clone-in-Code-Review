	private boolean onlyBranchesAreSelected(TreeSelection selection) {
		Iterator selIterator = selection.iterator();
		while (selIterator.hasNext()) {
			Object sel = selIterator.next();
			if (sel instanceof RefNode) {
				RefNode node = (RefNode) sel;
				String refName = node.getObject().getName();
				if (!refName.startsWith(R_HEADS)
						&& !refName.startsWith(R_REMOTES))
					return false;
			} else
				return false;
		}

		return true;
	}

