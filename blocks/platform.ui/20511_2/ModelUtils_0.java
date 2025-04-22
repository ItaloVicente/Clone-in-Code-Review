	
	public static String modelElementToBase64String(MApplicationElement element){
	  if( element instanceof EObject){
	    try {
	      EObject copy = EcoreUtil.copy((EObject) element);
	      
	      Resource binaryResource = new BinaryResourceImpl();
	      binaryResource.getContents().add(copy);
	      
	      ByteArrayOutputStream data = new ByteArrayOutputStream(1024);
	      binaryResource.save(data, null);
	      data.close();  // just for safety
	      
	      String back = Base64.encode(data.toByteArray());
	      return back;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	    
	  return null;
	}
	
	public static MApplicationElement base64StringToModelElement(String base64encodedModelElement) {
	  if( base64encodedModelElement == null ) return null;
	  
    try {
      ByteArrayInputStream data = new ByteArrayInputStream(Base64.decode(base64encodedModelElement));
      Resource binaryResource = new BinaryResourceImpl();
      binaryResource.load(data, null);
      data.close();
      return (MApplicationElement) binaryResource.getContents().get(0);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
