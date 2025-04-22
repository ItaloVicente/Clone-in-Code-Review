			ref = new DhtObjectIdRef(refName
		else
			detachingSymbolicRef = detach && ref.isSymbolic();

		if (detachingSymbolicRef) {
			RefData src = ((DhtRef) ref.getLeaf()).getRefData();
			RefData.Builder b = RefData.newBuilder(ref.getRefData());
			b.clearSymref();
			b.setTarget(src.getTarget());
			ref = new DhtObjectIdRef(refName
		}

