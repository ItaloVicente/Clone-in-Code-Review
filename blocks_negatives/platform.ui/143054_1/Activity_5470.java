        if (!Util.equals(id, castedObject.id)) {
            return false;
        }

        return Util.equals(name, castedObject.name);
    }

    void fireActivityChanged(ActivityEvent activityEvent) {
        if (activityEvent == null) {
