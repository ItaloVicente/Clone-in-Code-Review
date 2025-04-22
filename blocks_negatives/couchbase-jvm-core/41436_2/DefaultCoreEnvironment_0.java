            if (userAgent == null) {
                return String.format("%s (%s/%s %s; %s %s)", packageNameAndVersion,
                        System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"),
                        System.getProperty("java.vm.name"), System.getProperty("java.runtime.version"));
            } else {
                return userAgent;
            }
