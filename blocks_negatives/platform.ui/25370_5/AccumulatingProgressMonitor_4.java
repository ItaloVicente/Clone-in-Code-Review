        if (collector == null) {
            createCollector(null, work);
        } else {
            collector.worked(work);
        }
    }
