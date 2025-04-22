                String host = hostname;

                if (NetworkAddress.FORCE_IPV4) {
                    host = NetworkAddress.create(host).nameOrAddress();
                }

