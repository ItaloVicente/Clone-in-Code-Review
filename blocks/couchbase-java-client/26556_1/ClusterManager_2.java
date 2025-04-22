      try {
        request.setEntity(new StringEntity(sb.toString()));
        System.out.println(request.toString());
      } catch (UnsupportedEncodingException e) {
        getLogger().error("Error creating request. Bad arguments");
        throw new RuntimeException(e);
      }
      checkError(202, sendRequest(request));
