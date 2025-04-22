    @Override
    public boolean isFree() {
        if (pipeline) {
            return true;
        } else {
            return free;
        }
    }

