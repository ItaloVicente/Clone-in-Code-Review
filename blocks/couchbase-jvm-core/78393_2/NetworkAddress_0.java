            InetAddress[] addrs = InetAddress.getAllByName(input);
            InetAddress foundAddr = null;
            for (InetAddress addr : addrs) {
                if (addr instanceof Inet4Address) {
                    foundAddr = addr;
                    break;
                }
            }
            if (foundAddr == null) {
                throw new IllegalArgumentException("No IPv4 address found for \"" + input + "\"");
            }
            this.inner = foundAddr;
