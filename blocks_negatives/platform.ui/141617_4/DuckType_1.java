        for (int methodidx = 0; methodidx < methods.length; methodidx++) {
            Method method=methods[methodidx];
            try {
                candclass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        return true;
    }
