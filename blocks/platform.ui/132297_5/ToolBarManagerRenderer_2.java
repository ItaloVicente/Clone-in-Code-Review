				Set<String> allVariables = new HashSet<>();
				allVariables.addAll(updateVariables);

				visibleWhenTrackings.entrySet().forEach(e -> {
					if (getManager(e.getKey().toolbarModel) == null) {
						visibleWhenTrackings.remove(e.getKey());
					} else {
						e.setValue(new WeakReference<>(context));
						allVariables.addAll(Arrays.asList(e.getKey().getExpressionInfo().getAccessedVariableNames()));
					}
				});

				for (String var : allVariables) {
