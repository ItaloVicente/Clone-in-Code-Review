        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc exists");
            }
            this.specs.add(new LookupSpec(Lookup.EXIST, path));
        }
