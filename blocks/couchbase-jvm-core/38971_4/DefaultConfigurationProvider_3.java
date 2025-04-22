
        boolean tainted = config.tainted();
        for (Refresher refresher : refreshers.values()) {
            if (tainted) {
                refresher.markTainted(config);
            } else {
                refresher.markUntainted(config);
            }
        }

