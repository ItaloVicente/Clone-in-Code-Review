                if (object instanceof IAdaptable) {
                    ITaskListResourceAdapter taskListResourceAdapter;
                    Object adapter = ((IAdaptable) object)
                            .getAdapter(ITaskListResourceAdapter.class);
                    if (adapter != null
                            && adapter instanceof ITaskListResourceAdapter) {
                        taskListResourceAdapter = (ITaskListResourceAdapter) adapter;
                    } else {
                        taskListResourceAdapter = DefaultTaskListResourceAdapter
                                .getDefault();
                    }

                    IResource resource = taskListResourceAdapter
                            .getAffectedResource((IAdaptable) object);
                    if (resource != null) {
                        list.add(resource);
                    }
