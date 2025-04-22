	            list.deselectAll();
	        } else {
	            int n = in.size();
	            int[] ixs = new int[n];
	            int count = 0;
	            for (int i = 0; i < n; ++i) {
	                Object el = in.get(i);
	                int ix = getElementIndex(el);
	                if (ix >= 0) {
