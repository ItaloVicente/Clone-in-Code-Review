        } catch (NumberFormatException e) {
        }
        setComparator(new ResourceComparator(sortType));
    }

    /**
     * Restores the working set filter from the persistence store.
     */
    protected void initWorkingSetFilter() {
        String workingSetName = settings.get(STORE_WORKING_SET);

        IWorkingSet workingSet = null;

			IWorkingSetManager workingSetManager = getPlugin().getWorkbench()
					.getWorkingSetManager();
