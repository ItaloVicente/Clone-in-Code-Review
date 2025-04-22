	public List<E> simulateOn(List<E> list) {
		class ResultReference {
			List<E> value;
		}
		final ResultReference result = new ResultReference();
		result.value = list;
		accept(new ListDiffVisitor<E>() {
