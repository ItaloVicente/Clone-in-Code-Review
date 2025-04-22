			return;
		}

		if (getComparator() != null) {
			memento.putInteger(TAG_SORTER, getComparator().getCriteria());
		} else if (getSorter() != null) {
			memento.putInteger(TAG_SORTER, getSorter().getCriteria());
		}

		String filters[] = getPatternFilter().getPatterns();
		List selectedFilters = Arrays.asList(filters);
		List allFilters = FiltersContentProvider.getDefinedFilters();
		IMemento filtersMem = memento.createChild(TAG_FILTERS);
		for (Iterator iter = allFilters.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			IMemento child = filtersMem.createChild(TAG_FILTER);
			child.putString(TAG_ELEMENT, element);
			child.putString(TAG_IS_ENABLED, String.valueOf(selectedFilters.contains(element)));
		}

		if (frameList.getCurrentIndex() > 0) {
			TreeFrame currentFrame = (TreeFrame) frameList.getCurrentFrame();
			IMemento frameMemento = memento.createChild(TAG_CURRENT_FRAME);
			currentFrame.saveState(frameMemento);
		} else {
			Object expandedElements[] = viewer.getVisibleExpandedElements();
			if (expandedElements.length > 0) {
				IMemento expandedMem = memento.createChild(TAG_EXPANDED);
				for (Object expandedElement : expandedElements) {
					if (expandedElement instanceof IResource) {
						IMemento elementMem = expandedMem.createChild(TAG_ELEMENT);
						elementMem.putString(TAG_PATH, ((IResource) expandedElement).getFullPath().toString());
					}
				}
			}
