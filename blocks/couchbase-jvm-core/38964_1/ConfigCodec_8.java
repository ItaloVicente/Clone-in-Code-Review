                switch(currentStatus) {
                    case 200:
                        out.add(new BucketConfigResponse(currentConfig.toString(), ResponseStatus.SUCCESS));
                        break;
                    case 401:
                        out.add(new BucketConfigResponse("Unauthorized", ResponseStatus.FAILURE));
                        break;
                    case 404:
                        out.add(new BucketConfigResponse(currentConfig.toString(), ResponseStatus.NOT_EXISTS));
                        break;
                }

