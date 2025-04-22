        try {
            object.getClass().getMethod(method, args);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }
