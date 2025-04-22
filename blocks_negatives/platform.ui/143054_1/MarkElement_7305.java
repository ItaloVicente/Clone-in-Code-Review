        if (adapter == IWorkbenchAdapter.class)
            return (T)this;
        if (adapter == IPropertySource.class)
            return (T)new MarkElementProperties(this);
        return null;
    }

    @Override
