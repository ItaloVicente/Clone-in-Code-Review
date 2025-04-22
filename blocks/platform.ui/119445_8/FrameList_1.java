    public void reset() {
    	init();
        firePropertyChange(new PropertyChangeEvent(this, P_RESET,
                null, getFrame(current)));
    }

