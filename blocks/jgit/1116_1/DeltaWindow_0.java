				if (0 < maxMemory) {
					clear(res);
					int tail = next(resSlot);
					final long need = estimateSize(toSearch[off]);
					while (maxMemory < loaded + need && tail != resSlot) {
						clear(window[tail]);
						tail = next(tail);
					}
				}
