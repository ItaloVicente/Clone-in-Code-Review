	/**
	 * @param collection
	 *            a collection, into which all command groups (action sets)
	 *            which contribute <code>item</code> or its descendants will be
	 *            placed
	 * @param item	the item to collect descendants of
	 * @param filter the filter currently being used
	 * @param item
	 */
	private static void collectDescendantCommandGroups(Collection<ActionSet> collection,
			DisplayItem item, ViewerFilter filter) {
		List children = item.getChildren();
		for (Iterator i = children.iterator(); i.hasNext();) {
			DisplayItem child = (DisplayItem) i.next();
			if ((filter == null || filter.select(null, null, child))
					&& child.getActionSet() != null) {
				collection.add(child.getActionSet());
			}
			collectDescendantCommandGroups(collection, child, filter);
		}
	}

	/**
	 * Gets the keybindings associated with a ContributionItem.
	 */
	private Binding[] getKeyBindings(DisplayItem item) {
		IBindingService bindingService = (IBindingService) window
				.getService(IBindingService.class);

		if (!(bindingService instanceof BindingService)) {
			return new Binding[0];
		}

		String id = getCommandID(item);
		String param = getParamID(item);

		BindingManager bindingManager = ((BindingService) bindingService)
				.getBindingManager();

		Collection allBindings = bindingManager
				.getActiveBindingsDisregardingContextFlat();

		List<Binding> foundBindings = new ArrayList<Binding>(2);

		for (Iterator i = allBindings.iterator(); i.hasNext();) {
			Binding binding = (Binding) i.next();
			if (binding.getParameterizedCommand() == null) {
				continue;
			}
			if (binding.getParameterizedCommand().getId() == null) {
				continue;
			}
			if (binding.getParameterizedCommand().getId().equals(id)) {
				if (param == null) {
					foundBindings.add(binding);
				} else {
					Map m = binding.getParameterizedCommand().getParameterMap();
					String key = null;
					if (isNewWizard(item)) {
						key = IWorkbenchCommandConstants.FILE_NEW_PARM_WIZARDID;
					} else if (isShowView(item)) {
						key = IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID;
					} else if (isShowPerspective(item)) {
						key = IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID;
					}

					if (key != null) {
						if (param.equals(m.get(key))) {
							foundBindings.add(binding);
						}
					}
				}
			}
		}

		Binding[] bindings = foundBindings
				.toArray(new Binding[foundBindings.size()]);

		return bindings;
	}

	/**
	 * @param bindings
	 * @return a String representing the key bindings in <code>bindings</code>
	 */
	private String keyBindingsAsString(Binding[] bindings) {
		String keybindings = null;
		for (int i = 0; i < bindings.length; i++) {
			boolean alreadyRecorded = false;
			for (int j = 0; j < i && !alreadyRecorded; j++) {
				if (bindings[i].getTriggerSequence().equals(
						bindings[j].getTriggerSequence())) {
					alreadyRecorded = true;
				}
			}
			if (!alreadyRecorded) {
				String keybinding = bindings[i].getTriggerSequence().format();
				if (i == 0) {
					keybindings = keybinding;
				} else {
					keybindings = Util.createList(keybindings, keybinding);
				}
			}
		}
		return keybindings;
	}

