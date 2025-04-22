    private class ValueHashEnumerator extends HashEnumerator<V>{

    	public ValueHashEnumerator() {
    		super();
    	}

		@Override
		public V nextElement() {
    		if (hasMoreElements()) {
    			return entry.value;
    		}
    		throw new NoSuchElementException();
    	}
    }

