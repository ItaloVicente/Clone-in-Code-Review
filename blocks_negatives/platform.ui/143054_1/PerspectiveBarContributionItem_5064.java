            } else {
            }
        }
    }

    /**
     * Update this item with a new perspective descriptor
     * @param newDesc
     */
    public void update(IPerspectiveDescriptor newDesc) {
        perspective = newDesc;
        if (toolItem != null && !toolItem.isDisposed()) {
            ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
            if (imageDescriptor != null) {
                toolItem.setImage(imageDescriptor.createImage());
            } else {
                toolItem.setImage(WorkbenchImages.getImageDescriptor(
                        ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE)
                        .createImage());
            }
            toolItem.setToolTipText(NLS.bind(WorkbenchMessages.PerspectiveBarContributionItem_toolTip, perspective.getLabel() ));
        }
        update();
    }

    IWorkbenchPage getPage() {
        return workbenchPage;
    }

    IPerspectiveDescriptor getPerspective() {
        return perspective;
    }

    ToolItem getToolItem() {
        return toolItem;
    }

    /**
     * Answer whether the receiver is a match for the provided
     * perspective descriptor
     *
     * @param perspective the perspective descriptor
     * @param workbenchPage the page
     * @return <code>true</code> if it is a match
     */
    public boolean handles(IPerspectiveDescriptor perspective,
            IWorkbenchPage workbenchPage) {
        return this.perspective == perspective
                && this.workbenchPage == workbenchPage;
    }

    /**
     * Set the current perspective
     * @param newPerspective
     */
    public void setPerspective(IPerspectiveDescriptor newPerspective) {
        this.perspective = newPerspective;
    }

    void setSelection(boolean b) {
        if (toolItem != null && !toolItem.isDisposed()) {
