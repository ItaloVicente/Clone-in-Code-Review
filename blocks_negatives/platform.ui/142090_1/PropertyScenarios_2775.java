                try {
                    return new Double(currencyFormat.parse((String) fromObject).doubleValue());
                } catch (ParseException e) {
                    return new Double(0);
                }
            }
        };
