        if (indexDiffFilter == null) {
            return Collections.<String> emptySet();
        } else {
            HashSet<String> retVal = new HashSet<String>(indexDiffFilter.getUntrackedFolders());
            retVal.removeAll(submodules);
            return retVal;
        }
    }
