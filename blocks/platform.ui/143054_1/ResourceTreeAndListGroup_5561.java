					IProgressMonitor monitor) {
				for (Object element : elements) {
					returnValue.add(element);
				}
			}
		};

		try {
			getAllCheckedListItems(passThroughFilter, null);
		} catch (InterruptedException exception) {
			return Collections.EMPTY_LIST;
		}
		return returnValue;

	}

	public List getAllListItems() {
		final ArrayList returnValue = new ArrayList();

		IElementFilter passThroughFilter = new IElementFilter() {

			@Override
