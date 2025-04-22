
		if (repository != null && !nodesToShow.isEmpty()
				&& pathsByRepo.size() == 1) {
			lastSelectedRepository = repository.getDirectory();
		} else {
			lastSelectedRepository = null;
		}
		List<?> current = getCommonViewer().getStructuredSelection().toList();
		Set<?> currentlySelected = new HashSet<>(current);
		if (currentlySelected.containsAll(nodesToShow)) {
			getCommonViewer().getTree().showSelection();
		} else {
			selectReveal(new StructuredSelection(nodesToShow));
		}
