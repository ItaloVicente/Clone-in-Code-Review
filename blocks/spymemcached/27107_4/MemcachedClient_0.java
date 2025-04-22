    };

    DeleteOperation op = null;
    if(cas == 0) {
      op = opFact.delete(key, callback);
    } else {
      op = opFact.delete(key, cas, callback);
    }

