        managementTimeout = longPropertyOr("managementTimeout", builder.managementTimeout);
        queryTimeout = longPropertyOr("queryTimeout", builder.queryTimeout);
        viewTimeout = longPropertyOr("viewTimeout", builder.viewTimeout);
        kvTimeout = longPropertyOr("kvTimeout", builder.kvTimeout);
        connectTimeout = longPropertyOr("connectTimeout", builder.connectTimeout);
        disconnectTimeout = longPropertyOr("disconnectTimeout", builder.disconnectTimeout);
        dnsSrvEnabled = booleanPropertyOr("dnsSrvEnabled", builder.dnsSrvEnabled);
