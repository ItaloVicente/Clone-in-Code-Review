    	/*
    	* Initializes this view with the given view site.  A memento is passed to
        * the view which contains a snapshot of the views state from a previous
        * session.  Where possible, the view should try to recreate that state
        * within the part controls.
        * <p>
        * This implementation will ignore the memento and initialize the view in
        * a fresh state.  Subclasses may override the implementation to perform any
        * state restoration as needed.
        */
        init(site);
    }


    @Override
