            } else if (request instanceof RemoveBucketRequest) {
                response = new RemoveBucketResponse(status);
            } else if (request instanceof InsertBucketRequest) {
                response = new InsertBucketResponse(body, status);
            } else if (request instanceof UpdateBucketRequest) {
                response = new UpdateBucketResponse(body, status);
