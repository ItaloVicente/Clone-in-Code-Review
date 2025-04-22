                }

                /* merge filters from Memento with selected = true filters from plugins
                 * ensure there are no duplicates & don't override user preferences	 */
                List pluginFilters = FiltersContentProvider.getDefaultFilters();
                for (Iterator iter = pluginFilters.iterator(); iter.hasNext();) {
                    String element = (String) iter.next();
                    if (!selectedFilters.contains(element)
                            && !unSelectedFilters.contains(element)) {
