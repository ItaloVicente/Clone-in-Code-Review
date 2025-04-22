	public static Collector<Ref
			BinaryOperator<Ref> mergeFunction) {
		return Collectors.collectingAndThen(RefList.toRefList(mergeFunction)
				(refs) -> new RefMap(""
							refs
						RefList.emptyList()));
	}

