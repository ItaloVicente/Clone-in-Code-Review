                return Status.OK_STATUS;
            }
        };
        animationUpdateJob.setSystem(true);

        listener = getProgressListener();
        progressManager.addListener(listener);


    }

    /**
     * Add an item to the list
     *
     * @param item
     */
    void addItem(final AnimationItem item) {
        animationProcessor.addItem(item);
    }

    /**
     * Remove an item from the list
     *
     * @param item
     */
    void removeItem(final AnimationItem item) {
        animationProcessor.removeItem(item);
    }

    /**
     * Return whether or not the current state is animated.
     *
     * @return boolean
     */
    boolean isAnimated() {
        return animated;
    }

    /**
     * Set whether or not the receiver is animated.
     *
     * @param boolean
     */
    void setAnimated(final boolean bool) {
        animated = bool;
        animationUpdateJob.schedule(100);
    }

    /**
     * Dispose the images in the receiver.
     */
