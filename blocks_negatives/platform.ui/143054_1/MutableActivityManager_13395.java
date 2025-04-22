            }
        }

        Map activityPatternBindingDefinitionsByActivityId = ActivityPatternBindingDefinition
                .activityPatternBindingDefinitionsByActivityId(activityRegistry
                        .getActivityPatternBindingDefinitions());
        Map activityPatternBindingsByActivityId = new HashMap();

        for (Iterator iterator = activityPatternBindingDefinitionsByActivityId
                .entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String activityId = (String) entry.getKey();

            if (activityDefinitionsById.containsKey(activityId)) {
                Collection activityPatternBindingDefinitions = (Collection) entry
                        .getValue();

                if (activityPatternBindingDefinitions != null) {
					for (Iterator iterator2 = activityPatternBindingDefinitions
                            .iterator(); iterator2.hasNext();) {
                        ActivityPatternBindingDefinition activityPatternBindingDefinition = (ActivityPatternBindingDefinition) iterator2
                                .next();
                        String pattern = activityPatternBindingDefinition
                                .getPattern();

                        if (pattern != null && pattern.length() != 0) {
                            IActivityPatternBinding activityPatternBinding = new ActivityPatternBinding(
                                    activityId, pattern, activityPatternBindingDefinition.isEqualityPattern());
                            Set activityPatternBindings = (Set) activityPatternBindingsByActivityId
                                    .get(activityId);

                            if (activityPatternBindings == null) {
                                activityPatternBindings = new HashSet();
                                activityPatternBindingsByActivityId.put(
                                        activityId, activityPatternBindings);
                            }

                            activityPatternBindings.add(activityPatternBinding);
                        }
                    }
