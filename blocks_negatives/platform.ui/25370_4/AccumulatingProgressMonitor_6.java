        if (collector == null) {
            createCollector(name, 0);
        } else {
            collector.subTask(name);
        }
    }
