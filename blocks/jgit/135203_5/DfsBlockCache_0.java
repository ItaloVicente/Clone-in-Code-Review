			Ref<T> newRef = loader.load(val);
			if (ref != null) {
				if (newRef.size > ref.size) {
					reserveSpace(newRef.size - ref.size
				} else if (ref.size > newRef.size) {
					creditSpace(ref.size - newRef.size
				}
				ref.setValue(newRef);
				return ref.get();
			}

			ref = newRef;
