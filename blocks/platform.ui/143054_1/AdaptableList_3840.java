		if (adapter == IWorkbenchAdapter.class)
			return (T) this;
		return null;
	}

	public Object[] getChildren() {
		return children.toArray();
	}

	@Override
