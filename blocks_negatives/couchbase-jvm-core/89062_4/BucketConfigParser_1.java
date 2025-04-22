            InjectableValues inject = new InjectableValues.Std().addValue("env", env);
            return jackson()
                .readerFor(BucketConfig.class)
                .with(inject)
                .with(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .readValue(input);
