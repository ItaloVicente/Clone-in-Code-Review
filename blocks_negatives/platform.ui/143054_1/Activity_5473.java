    }

    boolean setActivityRequirementBindings(Set activityRequirementBindings) {
        activityRequirementBindings = Util.safeCopy(
                activityRequirementBindings, IActivityRequirementBinding.class);

        if (!Util.equals(activityRequirementBindings,
                this.activityRequirementBindings)) {
            this.activityRequirementBindings = activityRequirementBindings;
            this.activityRequirementBindingsAsArray = (IActivityRequirementBinding[]) this.activityRequirementBindings
                    .toArray(new IActivityRequirementBinding[this.activityRequirementBindings
                            .size()]);
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setActivityPatternBindings(Set activityPatternBindings) {
        activityPatternBindings = Util.safeCopy(activityPatternBindings,
                IActivityPatternBinding.class);

        if (!Util.equals(activityPatternBindings, this.activityPatternBindings)) {
            this.activityPatternBindings = activityPatternBindings;
            this.activityPatternBindingsAsArray = (IActivityPatternBinding[]) this.activityPatternBindings
                    .toArray(new IActivityPatternBinding[this.activityPatternBindings
                            .size()]);
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setDefined(boolean defined) {
        if (defined != this.defined) {
            this.defined = defined;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setEnabled(boolean enabled) {
        if (enabled != this.enabled) {
            this.enabled = enabled;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setName(String name) {
        if (!Util.equals(name, this.name)) {
            this.name = name;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    void setExpression(Expression exp) {
    	expression = exp;
    }

    boolean setDescription(String description) {
        if (!Util.equals(description, this.description)) {
            this.description = description;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    @Override
