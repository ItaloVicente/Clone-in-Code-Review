        if (this.vbucketsCount == config.getVbucketsCount()) {
            int vbucketsChanges = 0;
            for (int i = 0; i < this.vbucketsCount; i++) {
                vbucketsChanges += (this.getMaster(i) == config.getMaster(i)) ? 0 : 1;
            }
            difference.setVbucketsChanges(vbucketsChanges);
        } else {
            difference.setVbucketsChanges(-1);
        }
