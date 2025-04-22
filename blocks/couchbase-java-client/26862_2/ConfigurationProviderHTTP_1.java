
          if(this.buckets.get(bucketToFind) == null) {
            getLogger().warn("Bucket found, but has no bucket "
              + "configuration attached...skipping");
            continue;
          }

