              Bucket bucket = bucketEntry.getValue();
              List<String> servers = bucket.getConfig().getServers();
              for(String server : servers) {
                URI serverUri = URI.create("http://" + server);
                if(baseUri.getHost().equals(serverUri.getHost())) {
                  inList = true;
                  break;
                }
              }
