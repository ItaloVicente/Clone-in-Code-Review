    private boolean shouldRetry(int statusCode, HttpResponse response) {
      switch(statusCode) {
        case 200:
          return false;
        case 404:
          return analyse404Response(response);
        case 500:
          return analyse500Response(response);
        case 300:
        case 301:
        case 302:
        case 303:
        case 307:
        case 401:
        case 408:
        case 409:
        case 412:
        case 416:
        case 417:
        case 501:
        case 502:
        case 503:
        case 504:
          return true;
        default:
          return false;
      }
    }

    private boolean analyse404Response(HttpResponse response) {
      try {
        String body = EntityUtils.toString(response.getEntity());
        if(body.contains("not_found")
          && (body.contains("missing") || body.contains("deleted"))) {
          return false;
        }
      } catch(IOException ex) {
        return false;
      }
      return true;
    }

    private boolean analyse500Response(HttpResponse response) {
      try {
        String body = EntityUtils.toString(response.getEntity());
        if(body.contains("error")
          && body.contains(("{not_found, missing_named_view}"))) {
          return false;
        }
      } catch(IOException ex) {
        return false;
      }
      return true;
    }

