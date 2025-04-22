            if (!mapDescToRec.containsKey(desc)) {
                try {
                    SetRec rec;
                    if (invisibleBars.containsKey(desc)) {
                        rec = (SetRec) invisibleBars.get(desc);
                        if (rec.bars != null) {
                            rec.bars.activate();
                        }
                        invisibleBars.remove(desc);
                    } else {
                        IActionSet set = desc.createActionSet();
                        SubActionBars bars = new ActionSetActionBars(window
								.getActionBars(), window,
								(IActionBarConfigurer2) window.getWindowConfigurer()
										.getActionBarConfigurer(), desc.getId());
                        rec = new SetRec(set, bars);
                        set.init(window, bars);
                        sets.add(set);

                        Object[] existingRegistrations = window
                                .getExtensionTracker().getObjects(
                                        desc.getConfigurationElement()
                                                .getDeclaringExtension());
                        if (existingRegistrations.length == 0
                                || !containsRegistration(existingRegistrations,
                                        desc)) {
                            window.getExtensionTracker().registerObject(
                                    desc.getConfigurationElement().getDeclaringExtension(),
                                    desc, IExtensionTracker.REF_WEAK);
                        }
                    }
                    mapDescToRec.put(desc, rec);
                } catch (CoreException e) {
                    WorkbenchPlugin
                            .log("Unable to create ActionSet: " + desc.getId(), e);//$NON-NLS-1$
                }
            }
        }
        PluginActionSetBuilder.processActionSets(sets, window);

        iter = sets.iterator();
        while (iter.hasNext()) {
            PluginActionSet set = (PluginActionSet) iter.next();
            set.getBars().activate();
        }
    }

    /**
     * Return whether the array contains the given action set.
     *
     * @param existingRegistrations the array to check
     * @param set the set to look for
     * @return whether the set is in the array
     * @since 3.1
     */
    private boolean containsRegistration(Object[] existingRegistrations, IActionSetDescriptor set) {
        for (Object existingRegistration : existingRegistrations) {
            if (existingRegistration == set) {
