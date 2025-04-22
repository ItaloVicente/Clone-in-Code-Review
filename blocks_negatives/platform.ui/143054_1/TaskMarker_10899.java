        super.refresh();
        priority = getMarker().getAttribute(IMarker.PRIORITY,
                IMarker.PRIORITY_NORMAL);
        done = -1;
        if (getMarker().getAttribute(IMarker.USER_EDITABLE, true)) {
            done = 0;
            if (getMarker().getAttribute(IMarker.DONE, false)) {
                done = 1;
            }
        }
    }

    public int getPriority() {
        return priority;
    }

    public int getDone() {
        return done;
    }
