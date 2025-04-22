               Object parent = treeElement.getParent();
               if(parent != null && (deletions.contains(parent)
                       || additions.contains(parent))){
            	   obsoleteRefresh.add(treeElement);
               }

                if (!treeElement.isActive()) {
                    obsoleteRefresh.add(treeElement);
                    deletions.add(treeElement);
                }
            }

            refreshes.removeAll(obsoleteRefresh);

        }
    }

    /**
     * Create a new instance of the receiver.
     */
    ProgressViewUpdater() {
        createUpdateJob();
        collectors = new IProgressUpdateCollector[0];
    }

    @PostConstruct
    void init(MApplication application) {
    	progressManager.addListener(this);
    	application.getContext().set(ProgressViewUpdater.class, this);
    }

    /**
     * Add the new collector to the list of collectors.
     *
     * @param newCollector
     */
    void addCollector(IProgressUpdateCollector newCollector) {
        IProgressUpdateCollector[] newCollectors = new IProgressUpdateCollector[collectors.length + 1];
        System.arraycopy(collectors, 0, newCollectors, 0, collectors.length);
        newCollectors[collectors.length] = newCollector;
        collectors = newCollectors;
    }

    /**
     * Remove the collector from the list of collectors.
     *
     * @param provider
     */
    void removeCollector(IProgressUpdateCollector provider) {
        HashSet<IProgressUpdateCollector> newCollectors = new HashSet<>();
        for (int i = 0; i < collectors.length; i++) {
            if (!collectors[i].equals(provider)) {
				newCollectors.add(collectors[i]);
