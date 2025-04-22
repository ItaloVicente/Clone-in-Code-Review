    			list.removeAll();
    			startMeasuring();
    			for (int j = 0; j < items.length; j++) {
    				list.add(items[j]);
    			}
    			processEvents();
    			stopMeasuring();
    		}
        });
