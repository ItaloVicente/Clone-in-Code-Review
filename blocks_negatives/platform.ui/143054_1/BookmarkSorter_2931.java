    public void resetState() {
        priorities = new int[DEFAULT_PRIORITIES.length];
        System.arraycopy(DEFAULT_PRIORITIES, 0, priorities, 0,
                priorities.length);
        directions = new int[DEFAULT_DIRECTIONS.length];
        System.arraycopy(DEFAULT_DIRECTIONS, 0, directions, 0,
                directions.length);
    }
