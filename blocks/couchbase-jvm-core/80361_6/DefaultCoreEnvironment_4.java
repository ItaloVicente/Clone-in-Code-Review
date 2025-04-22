        continuousKeepAliveEnabled = booleanPropertyOr(
            "continuousKeepAliveEnabled",
                builder.continuousKeepAliveEnabled
        );
        keepAliveErrorThreshold = longPropertyOr("keepAliveErrorThreshold", builder.keepAliveErrorThreshold);
        keepAliveTimeout = longPropertyOr("keepAliveTimeout", builder.keepAliveTimeout);
