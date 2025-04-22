	            }
	            if (count < n) {
	                System.arraycopy(ixs, 0, ixs = new int[count], 0, count);
	            }
	            list.deselectAll();
	            list.select(ixs);
	        }
