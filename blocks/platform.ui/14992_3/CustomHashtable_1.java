    private class ValueHashEnumerator extends HashEnumerator<V>{

    	public ValueHashEnumerator() {
    		super();
    	}

    	public V nextElement() {
    		if (hasMoreElements()) {
    			return entry.value;
    		}
    		throw new NoSuchElementException();
    	}
    }

