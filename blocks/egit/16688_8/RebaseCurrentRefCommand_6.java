		ref = setRef(event);
		if (ref == null)
			return null;
		return super.execute(event);
	}

	private Ref setRef(ExecutionEvent event) throws ExecutionException {
