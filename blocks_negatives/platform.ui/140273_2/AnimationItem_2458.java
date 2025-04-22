            }
        });
        animationItem.addDisposeListener(e -> AnimationManager.getInstance().removeItem(AnimationItem.this));
        AnimationManager.getInstance().addItem(this);
    }

    /**
     * Create the animation item control.
     * @param parent the parent Composite
     * @return Control
     */
    protected abstract Control createAnimationItem(Composite parent);

    /**
     * Paint the image in the canvas.
     *
     * @param event
     *            The PaintEvent that generated this call.
     * @param image
     *            The image to display
     * @param imageData
     *            The array of ImageData. Required to show an animation.
     */
    void paintImage(PaintEvent event, Image image, ImageData imageData) {
        event.gc.drawImage(image, 0, 0);
    }

    /**
     * Get the SWT control for the receiver.
     *
     * @return Control
     */
    public abstract Control getControl();

    /**
     * The animation has begun.
     */
    void animationStart() {
        animationContainer.animationStart();
    }

    /**
     * The animation has ended.
     */
    void animationDone() {
        animationContainer.animationDone();
    }

    /**
     * Get the preferred width of the receiver.
     *
     * @return int
     */
    public int getPreferredWidth() {
        return AnimationManager.getInstance().getPreferredWidth() + 5;
    }

    /**
     * Set the container that will be updated when this runs.
     * @param container The animationContainer to set.
     */
    void setAnimationContainer(IAnimationContainer container) {
        this.animationContainer = container;
    }
