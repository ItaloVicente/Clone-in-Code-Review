        if (element instanceof IActivity) {
            IActivity activity = (IActivity) element;
            try {
                return activity.getName();
            } catch (NotDefinedException e) {
                return activity.getId();
            }
        } else if (element instanceof ICategory) {
            ICategory category = ((ICategory) element);
            try {
                return category.getName();
            } catch (NotDefinedException e) {
                return category.getId();
            }
        }
        return super.getText(element);
    }
