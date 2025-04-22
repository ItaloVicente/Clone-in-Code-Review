    public static Cursor getCursor(int code) {
        Display display = Display.getCurrent();
        if (cursors[code] == null) {
            ImageDescriptor source = null;
            ImageDescriptor mask = null;
            switch (code) {
            case LEFT:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_LEFT_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_LEFT_MASK);
                cursors[LEFT] = new Cursor(display, source.getImageData(), mask
                        .getImageData(), 16, 16);
                break;
            case RIGHT:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_RIGHT_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_RIGHT_MASK);
                cursors[RIGHT] = new Cursor(display, source.getImageData(),
                        mask.getImageData(), 16, 16);
                break;
            case TOP:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_TOP_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_TOP_MASK);
                cursors[TOP] = new Cursor(display, source.getImageData(), mask
                        .getImageData(), 16, 16);
                break;
            case BOTTOM:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_BOTTOM_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_BOTTOM_MASK);
                cursors[BOTTOM] = new Cursor(display, source.getImageData(),
                        mask.getImageData(), 16, 16);
                break;
            case CENTER:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_STACK_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_STACK_MASK);
                cursors[CENTER] = new Cursor(display, source.getImageData(),
                        mask.getImageData(), 16, 16);
                break;
            case OFFSCREEN:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_OFFSCREEN_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_OFFSCREEN_MASK);
                cursors[OFFSCREEN] = new Cursor(display, source.getImageData(),
                        mask.getImageData(), 16, 16);
                break;
            case FASTVIEW:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_TOFASTVIEW_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_TOFASTVIEW_MASK);
                cursors[FASTVIEW] = new Cursor(Display.getCurrent(), source
                        .getImageData(), mask.getImageData(), 16, 16);
                break;
            default:
            case INVALID:
                source = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_INVALID_SOURCE);
                mask = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJS_DND_INVALID_MASK);
                cursors[INVALID] = new Cursor(display, source.getImageData(),
                        mask.getImageData(), 16, 16);
                break;
            }
        }
        return cursors[code];
    }

    /**
     * Disposes all drag-and-drop cursors.
     */
    public static void dispose() {
        for (int idx = 0; idx < cursors.length; idx++) {
            cursors[idx].dispose();
            cursors[idx] = null;
        }
    }
