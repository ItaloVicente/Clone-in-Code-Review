        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            environment,
            Arrays.asList(errorLoader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, mock(Refresher.class));
            }}
        );
