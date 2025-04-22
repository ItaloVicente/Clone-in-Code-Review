		DeltaVisit nextDelta = nextDeltaFor(dv.a
		if (nextDelta != null)
			stack.push(nextDelta);

		DeltaVisit childDelta = firstChildDeltaOf(oe
		if (childDelta != null)
			stack.push(childDelta);
	}
