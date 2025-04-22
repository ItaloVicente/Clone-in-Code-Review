        if (this == o) {
            return true;
        }
        if (!(o instanceof StructuredSelection)) {
            return false;
        }
        StructuredSelection s2 = (StructuredSelection) o;

        if (isEmpty()) {
            return s2.isEmpty();
        }
        if (s2.isEmpty()) {
            return false;
        }

        boolean useComparer = comparer != null && comparer == s2.comparer;

        int myLen = elements.length;
        if (myLen != s2.elements.length) {
            return false;
        }
        for (int i = 0; i < myLen; i++) {
        	if (useComparer) {
                if (!comparer.equals(elements[i], s2.elements[i])) {
                    return false;
                }
        	} else {
	            if (!elements[i].equals(s2.elements[i])) {
	                return false;
	            }
        	}
        }
        return true;
    }

    @Override
	public Object getFirstElement() {
        return isEmpty() ? null : elements[0];
    }

    @Override
