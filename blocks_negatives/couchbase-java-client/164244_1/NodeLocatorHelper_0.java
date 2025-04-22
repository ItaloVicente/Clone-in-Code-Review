        configProvider = bucket
            .core()
            .<GetConfigProviderResponse>send(new GetConfigProviderRequest())
            .toBlocking()
            .single()
            .provider();
