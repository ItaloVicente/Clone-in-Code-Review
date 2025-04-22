            } else if (service.equals("ftsSSL")) {
                sslPorts.put(ServiceType.SEARCH, port);
            } else if (service.equals("cbas")) {
                ports.put(ServiceType.ANALYTICS, port);
            } else if (service.equals("cbasSSL")) {
                sslPorts.put(ServiceType.ANALYTICS, port);
