        NodePointer newParent = parent.createPath(context);
        if (isAttribute()) {
            NodePointer pointer = newParent.createAttribute(context, getName());
            pointer.setValue(value);
            return pointer;
        }
        if (parent instanceof NullPointer && parent.equals(newParent)) {
            throw createBadFactoryException(context.getFactory());
        }
        if (newParent instanceof EStructuralFeatureOwnerPointer) {
            EStructuralFeatureOwnerPointer pop = (EStructuralFeatureOwnerPointer) newParent;
            newParent = pop.getPropertyPointer();
        }
        return newParent.createChild(context, getName(), index, value);
    }

    @Override
