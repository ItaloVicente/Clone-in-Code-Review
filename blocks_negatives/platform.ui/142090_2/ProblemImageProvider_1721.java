        if (isMarkerType(marker, IMarker.PROBLEM)) {
            switch (marker.getAttribute(IMarker.SEVERITY,
                    IMarker.SEVERITY_WARNING)) {
            case IMarker.SEVERITY_ERROR:
            case IMarker.SEVERITY_WARNING:
            case IMarker.SEVERITY_INFO:
            }
        }
        return null;
    }
