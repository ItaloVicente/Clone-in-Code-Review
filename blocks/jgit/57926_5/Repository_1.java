		for (;;) {
			int v = useCnt.intValue();
			if (v == 0) {
				break;
			} else if (useCnt.compareAndSet(v
				if (v == 1) {
					doClose();
				}
				break;
			}
