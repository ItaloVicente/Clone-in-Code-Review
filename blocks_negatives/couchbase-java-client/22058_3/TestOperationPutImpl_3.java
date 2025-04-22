    InputStream bi;
    StringBuffer responseContent = new StringBuffer("");
    try {
      bi = response.getEntity().getContent();
      byte[] buffer = new byte[bi.available() ];
      responseContent.append(new String(buffer));
    } catch (IOException ex) {
      Logger.getLogger(TestOperationImpl.class.getName()).log(
              Level.SEVERE, "Could not read test response.", ex);
    } catch (IllegalStateException ex) {
      Logger.getLogger(TestOperationImpl.class.getName()).log(
              Level.SEVERE, null, ex);
    }


