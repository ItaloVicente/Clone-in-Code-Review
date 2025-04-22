        activityRequirementBindings = Util.safeCopy(
                activityRequirementBindings, IActivityRequirementBinding.class);

        if (!Util.equals(activityRequirementBindings,
                this.activityRequirementBindings)) {
            this.activityRequirementBindings = activityRequirementBindings;
            this.activityRequirementBindingsAsArray = this.activityRequirementBindings
                    .toArray(new IActivityRequirementBinding[this.activityRequirementBindings
                            .size()]);
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }
