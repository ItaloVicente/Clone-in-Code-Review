	protected void setResult(ISelection selection, Class<T> target) {
		List<T> selected = Collections.emptyList();
		if (selection instanceof IStructuredSelection && target != null) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			selected = ((List<?>) structured.toList()).stream().filter(p -> target.isInstance(p))
					.map(p -> target.cast(p)).collect(Collectors.toList());
		}
		setResult(selected);
	}

