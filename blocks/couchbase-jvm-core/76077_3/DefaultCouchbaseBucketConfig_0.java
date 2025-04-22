                String pair[] = rawHost.split(":");
                convertedHost = InetAddress.getByName(pair[0]);
                try {
                    directPort = Integer.parseInt(pair[1]);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Could not parse port from the node address: {}, fallback to 0", rawHost);
                    directPort = 0;
                }
