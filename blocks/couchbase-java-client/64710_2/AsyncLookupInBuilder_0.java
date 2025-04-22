        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc get");
            }
            this.specs.add(new LookupSpec(Lookup.GET, path));
        }
