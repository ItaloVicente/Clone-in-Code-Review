      }
    }

    private boolean shouldRetry(int statusCode) {
      switch(statusCode) {
        case 200:
          return false;
        case 300:
        case 301:
        case 302:
        case 303:
        case 307:
        case 401:
        case 404:
        case 408:
        case 409:
        case 412:
        case 416:
        case 417:
        case 500:
        case 501:
        case 502:
        case 503:
        case 504:
          return true;
        default:
          return false;
