        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter != null) {
            return adapter.getChildren(element);
        }
        return new Object[0];
    }
