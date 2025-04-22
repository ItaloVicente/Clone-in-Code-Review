    private boolean atLeastOneActiveInRequestQueue() {
        for (REQUEST elem : sentRequestQueue) {
            if (elem.isActive()) {
                return true;
            }
        }
        return false;
    }

