
        int id = CORE_ID.incrementAndGet();
        if (id < 0) {
            id = 1;
            CORE_ID.set(id);
        }
        this.coreId = id;
