        try {
            fixedModelRegistry.removeActivity(activity_to_listen.getId(),
                    activity_to_listen.getName());
        } catch (NotDefinedException e) {
            e.printStackTrace(System.err);
        }
