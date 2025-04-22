            singleHost = singleHost.trim();

            Matcher matcher = ipv6pattern.matcher(singleHost);
            if (singleHost.startsWith("[") && singleHost.endsWith("]")) {
                singleHost = singleHost.substring(1, singleHost.length() - 1);
                hosts.add(new InetSocketAddress(singleHost, 0));
            } else if (matcher.matches()) {
                hosts.add(new InetSocketAddress(
                    matcher.group(1),
                    Integer.parseInt(matcher.group(2)))
                );
