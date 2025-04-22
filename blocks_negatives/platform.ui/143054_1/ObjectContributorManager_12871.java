                } else {
                    for (Iterator it = adapters.iterator(); it.hasNext();) {
                        String adapter = (String) it.next();
                        if (!otherAdapters.contains(adapter)) {
                            it.remove();
                        }
                    }
                }
            }

            lastCommonTypes.clear();
            lastCommonTypes.addAll(classes);
            lastCommonTypes.addAll(interfaces);

            if (interfacesEmpty && classesEmpty && adapters.isEmpty()) {
                return null;
            }
        }

        ArrayList results = new ArrayList(4);
        ArrayList superClasses = new ArrayList(4);
        if (!classesEmpty) {
            for (int j = 0; j < classes.size(); j++) {
                if (classes.get(j) != null) {
                    superClasses.add(classes.get(j));
                }
            }
            if (!superClasses.isEmpty()) {
                results.add(superClasses.get(0));
            }
        }

        if (!interfacesEmpty) {
            removeCommonInterfaces(superClasses, interfaces, results);
        }

        if (!adapters.isEmpty()) {
            removeCommonAdapters(adapters, results);
            commonAdapters.addAll(adapters);
        }
        return results;
    }

    /**
     * Returns <code>true</code> if all objects in the given list are of the same class,
     * <code>false</code> otherwise.
