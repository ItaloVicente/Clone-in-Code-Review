            Object priorityObj = attributes.get(IMarker.PRIORITY);
            if (priorityObj != null && priorityObj instanceof Integer) {
                priority = ((Integer) priorityObj).intValue();
            }
        } else {
            priority = marker.getAttribute(IMarker.PRIORITY,
                    IMarker.PRIORITY_NORMAL);
        }
        return priority;
    }

    @Override
