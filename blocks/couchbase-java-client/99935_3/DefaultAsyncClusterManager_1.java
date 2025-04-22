        if (settings.ejectionMethod() != null) {
            if (settings.ejectionMethod() == EjectionMethod.FULL) {
                actual.put("evictionPolicy", "fullEviction");
            }
        }

