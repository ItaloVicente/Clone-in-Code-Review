            String name = null;
            ICategory category = (ICategory) element;
            try {
                name = category.getName();
            } catch (NotDefinedException e) {
                name = category.getId();
            }
            if (decorate && isLocked(category)) {
                name = NLS.bind(ActivityMessages.ActivitiesPreferencePage_lockedMessage, name);
            }
            return name;
        }
