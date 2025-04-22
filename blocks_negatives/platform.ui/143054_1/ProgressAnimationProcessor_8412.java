        return 30;
    }

    /**
     * Get the animation items currently registered for the receiver.
     *
     * @return ProgressAnimationItem[]
     */
    private ProgressAnimationItem[] getAnimationItems() {
        ProgressAnimationItem[] animationItems = new ProgressAnimationItem[items
                .size()];
        items.toArray(animationItems);
        return animationItems;
    }

    @Override
