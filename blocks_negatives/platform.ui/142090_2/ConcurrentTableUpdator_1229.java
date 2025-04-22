	        	Object obj = knownObjects[row];
	        	if (obj != null && obj != sentObjects[idx]) {
	        		table.replace(obj, row);
	        		sentObjects[idx] = obj;
	        	}
	        }
