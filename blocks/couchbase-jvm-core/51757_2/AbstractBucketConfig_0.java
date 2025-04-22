            for (ServiceType type : info.services().keySet()) {
                es |= 1 << type.ordinal();
            }
            for (ServiceType type : info.sslServices().keySet()) {
                es |= 1 << type.ordinal();
            }
