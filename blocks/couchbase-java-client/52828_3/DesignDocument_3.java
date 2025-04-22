        boolean hasOptions = false;
        for (Map.Entry<Option, Long> entry : options.entrySet()) {
            hasOptions = true;
            opts.put(entry.getKey().alias(), entry.getValue());
        }

