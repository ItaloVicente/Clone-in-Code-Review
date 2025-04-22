        if (sk.isValid() && sk.isReadable()) {
          handleReads(node);
        }
        if (sk.isValid() && sk.isWritable()) {
          handleWrites(node);
        }
