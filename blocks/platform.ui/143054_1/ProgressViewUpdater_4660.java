				if (deletions.contains(treeElement) || additions.contains(treeElement)) {
					obsoleteRefresh.add(treeElement);
				}

				Object parent = treeElement.getParent();
				if (parent != null && (deletions.contains(parent) || additions.contains(parent))) {
