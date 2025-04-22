        try {
        } catch(ClassNotFoundException e) {
            return new Class[0];
        }
        return new Class[] { IPropertySource.class };
    }
