                String parts[] = rawHost.split(":");
                String host = "";
                String port = parts[parts.length - 1];
                if (parts.length > 2) {
                    for (int i = 0; i < parts.length - 1; i++) {
                        host += parts[i];
                        if (parts[i].endsWith("]")) {
                            break;
                        } else {
                            host += ":";
                        }
                    }
                } else {
                    host = parts[0];
                }

                convertedHost = NetworkAddress.create(host);
