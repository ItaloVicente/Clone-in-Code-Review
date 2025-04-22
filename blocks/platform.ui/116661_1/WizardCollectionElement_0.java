    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WizardCollectionElement)) {
            return false;
        }
        WizardCollectionElement other = (WizardCollectionElement) obj;
        return Objects.equals(this.getId(), other.getId()) && Objects.equals(this.name, other.name)
        && Objects.equals(this.getPluginId(), other.getPluginId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), this.name, getPluginId());
    }

