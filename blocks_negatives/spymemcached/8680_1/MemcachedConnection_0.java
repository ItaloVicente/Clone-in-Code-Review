			try {
				MemcachedNode qa=null;
				while((qa=addedQueue.remove()) != null) {
					todo.add(qa);
				}
			} catch(NoSuchElementException e) {
