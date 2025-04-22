        if (workingSet == null || (workingSet.isAggregateWorkingSet() &&
        		workingSet.isEmpty())) {
            return true;
        }
        
        IResource resource = Adapters.adapt(element, IResource.class);
        if (resource != null) {
            return isEnclosed(resource);
        }
        return true;
    }
