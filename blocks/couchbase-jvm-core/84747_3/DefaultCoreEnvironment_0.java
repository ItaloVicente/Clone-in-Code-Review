
        if (configPollInterval < configPollFloorInterval) {
            throw new IllegalArgumentException("The config poll interval (" +
                configPollInterval + ") is lower than the config " +
                "poll floor interval (" + configPollFloorInterval +
                "), which means that some config polling will " +
                "be skipped. Please adjust both settings in a logical fashion.");
        }
