				if (object instanceof IAdaptable) {
					ITaskListResourceAdapter taskListResourceAdapter = Adapters.getAdapter(object,
							ITaskListResourceAdapter.class, true);
					if (taskListResourceAdapter == null) {
						taskListResourceAdapter = DefaultTaskListResourceAdapter.getDefault();
					}

					IResource resource = taskListResourceAdapter.getAffectedResource((IAdaptable) object);
					if (resource != null) {
						list.add(resource);
					}
