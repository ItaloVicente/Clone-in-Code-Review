    public void setId(String id);

    /**
     * Sets the name of the working set. 
     * The working set name should be unique.
     * The working set name must not have leading or trailing 
     * whitespace.
     * 
     * @param name the name of the working set
     */
    public void setName(String name);
    
    /**
     * Returns whether this working set can be edited or not. To make
     * a working set editable the attribute <code>pageClass</code> of
     * the extension defining a working set must be provided.
     * 
     * @return <code>true</code> if the working set can be edited; otherwise
     *  <code>false</code>
     *  
     * @since 3.1
     */
    public boolean isEditable();
 
    /**
