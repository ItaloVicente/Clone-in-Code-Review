	private void setDeltaDepth(ObjectToPack otp) {
		LinkedList<ObjectToPack> deltaChain = new LinkedList<ObjectToPack>();
		int deltaDepth = 0;
		for (ObjectToPack cur = otp;;) {
			if (cur == null || !cur.isDeltaRepresentation() || cur.isEdge())
				break;
			int curDeltaDepth = cur.getDeltaDepth();
			if (curDeltaDepth > 0) {
				deltaDepth = curDeltaDepth;
				break;
			}

			deltaChain.push(cur);
			cur = objectsMap.get(cur.getDeltaBaseId());
		}

		while (!deltaChain.isEmpty())
			deltaChain.pop().setDeltaDepth(++deltaDepth);
	}

