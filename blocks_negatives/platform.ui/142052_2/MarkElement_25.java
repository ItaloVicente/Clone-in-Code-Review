        IWorkbenchAdapter parentElement = Adapters.adapt(parent, IWorkbenchAdapter.class);
        if (parentElement != null) {
            return parentElement.getImageDescriptor(object);
        }
        return null;
    }

    @Override
