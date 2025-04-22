	private void selectDeltaBase(DeltaWindowEntry src
		bestBase = src;

		if (delta instanceof ArrayStream) {
			ArrayStream a = (ArrayStream) delta;
			deltaBuf = a.buf;
			deltaLen = a.cnt;
		} else {
			TemporaryBuffer.Heap b = (TemporaryBuffer.Heap) delta;
			deltaBuf = b;
			deltaLen = (int) b.length();
		}
	}

