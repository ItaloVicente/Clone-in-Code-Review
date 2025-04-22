                            Set activityRequirementBindings = (Set) activityRequirementBindingsByActivityId
                                    .get(parentActivityId);

                            if (activityRequirementBindings == null) {
                                activityRequirementBindings = new HashSet();
                                activityRequirementBindingsByActivityId.put(
                                        parentActivityId,
                                        activityRequirementBindings);
                            }
