        replicas[j - 1] = (short) rows.getInt(j);
      }
      VBucket vbucket;
      switch (replicaSize) {
        case 0:
          vbucket = new VBucket(master);
          break;
        case 1:
          vbucket = new VBucket(master, replicas[0]);
          break;
        case 2:
          vbucket = new VBucket(master, replicas[0], replicas[1]);
          break;
        case 3:
          vbucket = new VBucket(master, replicas[0], replicas[1], replicas[2]);
          break;
        default:
          throw new IllegalStateException("Not more than 3 replicas supported");
