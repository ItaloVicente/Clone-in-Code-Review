		boolean done=false;
		for(int i=0; !done && i<max; i++) {
			CASValue<T> casval=client.gets(key, transcoder);
			T current=null;
			if(casval != null) {
				T tmp = casval.getValue();
				current=tmp;
			}
			if(current != null) {
				assert casval != null : "casval was null with a current value";
