    public SquashOption(VersionRecord record) {
        super(null,
              record.author(),
              record.email(),
              record.comment(),
              record.date(),
              null);
        this.setRecord(record);
    }
