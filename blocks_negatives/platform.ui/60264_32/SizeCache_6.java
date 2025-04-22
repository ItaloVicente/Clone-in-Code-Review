        cachedWidthQuery = -1;
        cachedWidthResult = -1;
        cachedHeightQuery = -1;
        cachedHeightResult = -1;
        minimumWidth = -1;
        maximumWidth = -1;
        minimumHeight = -1;
        heightAtMinimumWidth = -1;
        widthAtMinimumHeight = -1;

        if (recursive || dirtySize != null) {
            if (control == null || control.isDisposed()) {
                dirtySize = new Point(0,0);
                control = null;
            } else {
                dirtySize = control.getSize();
            }
        }

        this.flushChildren = this.flushChildren || recursive;
