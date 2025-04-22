		if (out.size() > 0) {
			@SuppressWarnings("unchecked")
			E[] newArrayInstance = (E[]) Array.newInstance(element.getClass(),
					out.size());
			return out.toArray(newArrayInstance);
		}
		@SuppressWarnings("unchecked")
