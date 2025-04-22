    	v.remove(element);
    	v.remove(element1);
    }

    public void testRevealBug69076() {
    	if (Util.isCarbon() || Util.isLinux()) {
    		return;
    	}
