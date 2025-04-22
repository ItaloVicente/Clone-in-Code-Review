        while (iterator.hasNext()) {
            Object object = iterator.next();
            Util.assertInstance(object,
                    ActivityRequirementBindingDefinition.class);
            ActivityRequirementBindingDefinition activityRequirementBindingDefinition = (ActivityRequirementBindingDefinition) object;
            String parentActivityId = activityRequirementBindingDefinition
                    .getActivityId();
