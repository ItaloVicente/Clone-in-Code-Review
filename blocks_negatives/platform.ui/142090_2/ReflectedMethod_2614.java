        this.subject = subject;
        method = null;
        try {
        	method = subject.getClass().getMethod(methodName, paramTypes);
        } catch (Exception e) {
        	System.out.println(e);
        }
    }
