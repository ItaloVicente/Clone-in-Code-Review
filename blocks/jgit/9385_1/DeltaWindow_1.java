	private void checkLoadable(DeltaWindowEntry ent
			int tail = next(resSlot);
			while (maxMemory < loaded + need) {
				DeltaWindowEntry cur = window[tail];
				clear(cur);
				if (cur == ent) {
					LargeObjectException.ExceedsLimit e =
						 new LargeObjectException.ExceedsLimit(maxMemory
					e.setObjectId(ent.object);
					throw e;
				}
				tail = next(tail);
			}
	}

