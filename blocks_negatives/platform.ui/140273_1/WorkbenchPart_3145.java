    		partProperties.remove(key);
    	} else {
    		partProperties.put(key, value);
    	}
    	firePartPropertyChanged(key, oldValue, value);
    }

    @Override
