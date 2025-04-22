			result = (T[]) Array.newInstance(a.getClass().getComponentType(),
					size);
		}

		int i = 0;
		for (IdentityWrapper<? extends E> wrapper : wrappedSet) {
			result[i++] = (T) wrapper.unwrap();
