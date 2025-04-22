    }

    private void paintCompositeMaxUnknown(GC gc) {
        Rectangle rect = getClientArea();
        int x = rect.x;
        int y = rect.y;
        int w = rect.width;
        int h = rect.height;
        if (bgCol != null) {
