        this.value = null;
        if (parent != null) {
            parent.remove();
        }
        else {
            throw new UnsupportedOperationException(
                "Cannot remove an object that is not "
                    + "some other object's property or a collection element");
        }
    }

    /**
     * Get a PropertyPointer for this PropertyOwnerPointer.
     * @return PropertyPointer
     */
    public abstract EStructuralFeaturePointer getPropertyPointer();

    /**
     * Learn whether dynamic property declaration is supported.
     * @return true if the property owner can set a property "does not exist".
     *         A good example is a Map. You can always assign a value to any
     *         key even if it has never been "declared".
     */
    public boolean isDynamicPropertyDeclarationSupported() {
        return false;
    }

    @Override
