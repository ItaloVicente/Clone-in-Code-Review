	        boolean isValid = isValid();
	        if (isValid != oldState) {
				fireStateChanged(IS_VALID, oldState, isValid);
			}
	        return isValid;
	    }
