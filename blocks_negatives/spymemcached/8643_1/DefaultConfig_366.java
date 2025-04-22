    @Override
    public ConfigDifference compareTo(Config config) {
        ConfigDifference difference = new ConfigDifference();

        if (this.serversCount == config.getServersCount()) {
            difference.setSequenceChanged(false);
            for (int i = 0; i < this.serversCount; i++) {
                if (!this.getServer(i).equals(config.getServer(i))) {
                    difference.setSequenceChanged(true);
                    break;
                }
            }
        } else {
            difference.setSequenceChanged(true);
        }

        if (this.vbucketsCount == config.getVbucketsCount()) {
            int vbucketsChanges = 0;
            for (int i = 0; i < this.vbucketsCount; i++) {
                vbucketsChanges += (this.getMaster(i) == config.getMaster(i)) ? 0
                        : 1;
            }
            difference.setVbucketsChanges(vbucketsChanges);
        } else {
            difference.setVbucketsChanges(-1);
        }

        return difference;
    }
