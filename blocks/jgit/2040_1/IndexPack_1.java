        IObjectChecker checker=(objCheck==null)?null:objCheck.checkerFor(type);
		if (checker != null) {
            ByteArrayOutputStream destinationForInflatedObjectData=new ByteArrayOutputStream((int) sz);
            updateTempObjectIdWithDigestForPackedObject(type
            byte[] data = destinationForInflatedObjectData.toByteArray();
            try {
                checker.check(data);
