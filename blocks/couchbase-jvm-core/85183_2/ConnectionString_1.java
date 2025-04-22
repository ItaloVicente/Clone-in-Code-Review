                String[] parts = singleHost.split(":");
                if (parts.length == 1) {
                    hosts.add(new InetSocketAddress(parts[0], 0));
                } else {
                    hosts.add(new InetSocketAddress(parts[0], Integer.parseInt(parts[1])));
                }
