		Object[] result = a;
		if (result.length < doGetSize()) {
			result = (Object[]) Array.newInstance(a.getClass()
					.getComponentType(), doGetSize());
		}
		int offset = 0;
		for (int i = 0; i < lists.length; i++) {
			Object[] oa = lists[i].toArray();
			System.arraycopy(oa, 0, result, offset, oa.length);
			offset += lists[i].size();
		}

		return result;
