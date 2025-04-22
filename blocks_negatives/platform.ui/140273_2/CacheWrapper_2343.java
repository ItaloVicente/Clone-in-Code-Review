    private class WrapperLayout extends Layout implements ICachingLayout {
        @Override
		protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            Control[] children = composite.getChildren();
            if (children.length != 1) {
                return new Point(0, 0);
            }
