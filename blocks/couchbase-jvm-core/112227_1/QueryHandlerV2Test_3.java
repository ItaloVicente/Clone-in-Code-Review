                @Override
                protected CoreEnvironment env() {
                    return env;
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(testHandler);
