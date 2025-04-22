        this.titleImage = titleImage;
        firePropertyChange(IIntroPart.PROP_TITLE);
    }

    /**
     * Set the title string for this part.
     *
     * @param titleLabel the title string.  Must not be <code>null</code>.
     * @since 3.2
     */
    protected void setTitle(String titleLabel) {
    	Assert.isNotNull(titleLabel);
    	if (Util.equals(this.titleLabel, titleLabel))
    		return;
    	this.titleLabel = titleLabel;
    	firePropertyChange(IIntroPart.PROP_TITLE);
    }
