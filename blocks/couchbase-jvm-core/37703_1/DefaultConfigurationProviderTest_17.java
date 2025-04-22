        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            environment,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, mock(Refresher.class));
            }}
        );
