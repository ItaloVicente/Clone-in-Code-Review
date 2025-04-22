				}

				String[] patternArray = new String[selectedFilters.size()];
				selectedFilters.toArray(patternArray);
				getPatternFilter().setPatterns(patternArray);

			} else { // filters defined, old version: ignore filters from plugins
				String filters[] = new String[children.length];
				for (int i = 0; i < children.length; i++) {
					filters[i] = children[i].getString(TAG_ELEMENT);
				}
				getPatternFilter().setPatterns(filters);
			}
		} else { // no filters defined, old version: ignore filters from plugins
			getPatternFilter().setPatterns(new String[0]);
		}
	}

	protected void restoreState(IMemento memento) {
		TreeViewer viewer = getTreeViewer();
		IMemento frameMemento = memento.getChild(TAG_CURRENT_FRAME);

		if (frameMemento != null) {
			TreeFrame frame = new TreeFrame(viewer);
			frame.restoreState(frameMemento);
			frame.setName(getFrameName(frame.getInput()));
			frame.setToolTipText(getFrameToolTipText(frame.getInput()));
			viewer.setSelection(new StructuredSelection(frame.getInput()));
			frameList.gotoFrame(frame);
		} else {
			IContainer container = ResourcesPlugin.getWorkspace().getRoot();
			IMemento childMem = memento.getChild(TAG_EXPANDED);
			if (childMem != null) {
				ArrayList elements = new ArrayList();
