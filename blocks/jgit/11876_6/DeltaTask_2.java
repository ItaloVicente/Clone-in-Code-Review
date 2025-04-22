			DeltaWindow w;
			for (;;) {
				synchronized (this) {
					if (slices.isEmpty())
						break;
					w = initWindow(slices.removeFirst());
				}
				runWindow(w);
