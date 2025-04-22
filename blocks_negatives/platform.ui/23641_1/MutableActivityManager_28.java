        MutableActivityManager clone = new MutableActivityManager(advisor, activityRegistry);
        clone.setEnabledActivityIds(getEnabledActivityIds());
        return clone;
    }
    
    /**
     * Return the identifier update job.
     * 
     * @return the job
     * @since 3.1
     */
    private Job getUpdateJob() {
        if (deferredIdentifierJob == null) {
                
                /* (non-Javadoc)
                 * @see org.eclipse.core.internal.jobs.InternalJob#run(org.eclipse.core.runtime.IProgressMonitor)
                 */
                protected IStatus run(IProgressMonitor monitor) {
                    while (!deferredIdentifiers.isEmpty()) {
                        Identifier identifier = (Identifier) deferredIdentifiers.remove(0);
                        Set activityIds = new HashSet();
                        for (Iterator iterator = definedActivityIds.iterator(); iterator
                                .hasNext();) {
                            String activityId = (String) iterator.next();
                            Activity activity = (Activity) getActivity(activityId);

                            if (activity.isMatch(identifier.getId())) {
                                activityIds.add(activityId);
                            }
                        }
                        
                        boolean activityIdsChanged = identifier.setActivityIds(activityIds);
                        if (activityIdsChanged) {
                            IdentifierEvent identifierEvent = new IdentifierEvent(identifier, activityIdsChanged,
                                    false);
                            final Map identifierEventsByIdentifierId = new HashMap(1);
                            identifierEventsByIdentifierId.put(identifier.getId(),
                                    identifierEvent);

								public IStatus runInUIThread(
										IProgressMonitor monitor) {
