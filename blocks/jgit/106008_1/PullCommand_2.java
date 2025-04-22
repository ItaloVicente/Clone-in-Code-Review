
    private boolean matches(URIish a
        if (a.isRemote() && b.isRemote()) {
            return a.toString().equals(b.toString());
        }
        if (!a.isRemote() && !b.isRemote()) {
            String ap = a.getRawPath();
            String bp = b.getRawPath();
            ap = ap.substring(0
            bp = bp.substring(0
            return ap.equals(bp);
        }
        return false;
    }
