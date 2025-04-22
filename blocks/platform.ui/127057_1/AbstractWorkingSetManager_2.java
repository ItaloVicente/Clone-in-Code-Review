				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	private Map<WorkingSetDescriptor, List<IWorkingSet>> getNonEmpty(final List<WorkingSetDescriptor> descriptors) {
		Map<WorkingSetDescriptor, List<IWorkingSet>> map = descriptors.stream()
				.collect(Collectors.toMap(d -> d, d -> getWorkingSetsForId(d.getId())));
		Iterator<Entry<WorkingSetDescriptor, List<IWorkingSet>>> iterator = map.entrySet().iterator();
		iterator.forEachRemaining(e -> {
			if (e.getValue().isEmpty()) {
				iterator.remove();
			}
		});
		return map;
	}

	private static List<WorkingSetDescriptor> getUniqueDescriptors(String symbolicName) {
		final List<WorkingSetDescriptor> descriptors = WorkbenchPlugin.getDefault()
				.getWorkingSetRegistry().getUpdaterDescriptorsForNamespace(symbolicName);
		return descriptors;
