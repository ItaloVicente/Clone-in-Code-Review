		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 0) {
				if (all) {
					for (Object element : structuredSelection.toArray()) {
						if (!checkRepositoryHasHead(element)) {
							return false;
						}
					}
					return true;
