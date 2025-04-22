					for (Iterator iterator2 = activityRequirementBindingDefinitions
                            .iterator(); iterator2.hasNext();) {
                        ActivityRequirementBindingDefinition activityRequirementBindingDefinition = (ActivityRequirementBindingDefinition) iterator2
                                .next();
                        String childActivityId = activityRequirementBindingDefinition
                                .getRequiredActivityId();
