  public void deleteBucket(String name) {
    String url = "/pools/default/buckets/" + name;
    BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("DELETE", url);

    checkError(200, sendRequest(request));
