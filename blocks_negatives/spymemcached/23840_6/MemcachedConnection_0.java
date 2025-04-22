        if (sk.isValid() && sk.isReadable()) {
          handleReads(sk, qa);
        }
        if (sk.isValid() && sk.isWritable()) {
          handleWrites(sk, qa);
        }
