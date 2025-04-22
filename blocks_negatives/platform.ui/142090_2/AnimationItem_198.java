        }
    };

    /**
     * Create a new instance of the receiver.
     *
     * @param workbenchWindow
     *            the window being created
     */
    public AnimationItem(AnimationManager animationManager) {
    	this.animationManager = animationManager;
    }

    /**
     * Create the canvas that will display the image.
     *
     * @param parent
     */
    public void createControl(Composite parent) {

        Control animationItem = createAnimationItem(parent);
