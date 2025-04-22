        if (shuffle) {
            List<InetAddress> hostsList = new ArrayList<InetAddress>(hosts);
            Collections.shuffle(hostsList);
            this.seedHosts.set(new HashSet<InetAddress>(hostsList));
        } else {
            this.seedHosts.set(hosts);
        }
