        final CategoryActivityBinding castedObject = (CategoryActivityBinding) object;
        if (!Util.equals(activityId, castedObject.activityId)) {
            return false;
        }

        return Util.equals(categoryId, castedObject.categoryId);
    }
