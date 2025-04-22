        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
