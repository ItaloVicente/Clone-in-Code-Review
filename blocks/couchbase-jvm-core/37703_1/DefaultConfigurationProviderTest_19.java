            .thenReturn(Observable.from(Tuple.create(LoaderType.Carrier, mock(BucketConfig.class))));

        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            environment,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, mock(Refresher.class));
            }}
        );
