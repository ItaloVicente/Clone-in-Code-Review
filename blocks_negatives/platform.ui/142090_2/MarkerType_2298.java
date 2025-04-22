            if (!result.contains(sup)) {
                result.add(sup);
                sup.getAllSupertypes(result);
            }
        }
    }

    /**
