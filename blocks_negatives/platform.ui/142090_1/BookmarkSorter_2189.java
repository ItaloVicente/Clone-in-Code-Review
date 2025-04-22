        }

        if (index == -1) {
            resetState();
            return;
        }

        for (int i = index; i > 0; i--) {
            priorities[i] = priorities[i - 1];
        }
        priorities[0] = priority;
        directions[priority] = DEFAULT_DIRECTIONS[priority];
    }

    public void setTopPriorityDirection(int direction) {
        if (direction == ASCENDING || direction == DESCENDING) {
			directions[priorities[0]] = direction;
