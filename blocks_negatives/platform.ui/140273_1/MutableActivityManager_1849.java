                    .hasNext();) {
                IActivityRequirementBinding activityRequirementBinding = iterator2
                        .next();
                childActivityIds.add(activityRequirementBinding
                        .getRequiredActivityId());
            }

            childActivityIds.removeAll(requiredActivityIds);
            requiredActivityIds.addAll(childActivityIds);
            getRequiredActivityIds(childActivityIds, requiredActivityIds);
        }
    }
