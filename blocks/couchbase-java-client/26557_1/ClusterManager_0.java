
      List<String> buckets = listBuckets();
      if(buckets.contains(name)){
        throw new RuntimeException("Bucket with given name already exists");
      } else {
      BasicHttpEntityEnclosingRequest request =
