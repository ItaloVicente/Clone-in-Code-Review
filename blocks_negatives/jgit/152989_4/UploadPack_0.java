		List<Ref> sorted = new ArrayList<>(visibleRefs.size());
		sorted.addAll(filterRefByPredicate(visibleRefs, startsWithRefsHeads));
		sorted.addAll(filterRefByPredicate(visibleRefs, startsWithRefsTags));
		sorted.addAll(filterRefByPredicate(visibleRefs, allOther));

		return sorted;
