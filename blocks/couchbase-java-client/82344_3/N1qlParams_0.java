        if (this.pipelineCap != null) {
            queryJson.put("pipeline_cap", this.pipelineCap.toString());
        }
        if (this.pipelineBatch != null) {
            queryJson.put("pipeline_batch", this.pipelineBatch.toString());
        }
        if (this.scanCap != null) {
            queryJson.put("scan_cap", this.scanCap.toString());
        }
