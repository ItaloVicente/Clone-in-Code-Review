
            @Override
            protected CoreEnvironment env() {
                return DefaultCoreEnvironment.builder()
                        .continuousKeepAliveEnabled(false).build();
            }
