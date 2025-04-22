    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) {
        if (data instanceof Hashtable) {
            Hashtable table = (Hashtable)data;
            allowAdvanced = Boolean.valueOf((String) table.remove(ALLOW_ADVANCED)).booleanValue();
            strings.putAll(table);
        }
    }

    @Override
