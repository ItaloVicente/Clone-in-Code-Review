    public void execute() {
        final StoredConfig cfg = repo.getConfig();
        consumer.accept(cfg);
        try {
            cfg.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
