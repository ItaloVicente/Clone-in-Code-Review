							if (activityRequirementBindings == null) {
								activityRequirementBindings = new HashSet<>();
								activityRequirementBindingsByActivityId.put(parentActivityId,
										activityRequirementBindings);
							}

							activityRequirementBindings.add(activityRequirementBinding);
						}
