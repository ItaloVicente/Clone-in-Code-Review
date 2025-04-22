        while (iterator.hasNext()) {
            Object object = iterator.next();
            Util.assertInstance(object, ActivityPatternBindingDefinition.class);
            ActivityPatternBindingDefinition activityPatternBindingDefinition = (ActivityPatternBindingDefinition) object;
            String activityId = activityPatternBindingDefinition
                    .getActivityId();
