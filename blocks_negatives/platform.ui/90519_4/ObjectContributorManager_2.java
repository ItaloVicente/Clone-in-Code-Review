        for (int i = 0; i < interfaces.length; i++) {
            Class interfac = interfaces[i];
            if (seen.get(interfac) == null) {
                result.add(interfac);
                seen.put(interfac, interfac);
                newInterfaces.add(interfac);
