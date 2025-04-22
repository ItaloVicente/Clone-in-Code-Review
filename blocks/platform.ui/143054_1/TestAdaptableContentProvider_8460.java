		IWorkbenchAdapter adapter = getAdapter(element);
		if (adapter != null) {
			return adapter.getParent(element);
		}
		return null;
	}

	@Override
