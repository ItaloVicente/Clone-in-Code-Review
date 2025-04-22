            switch (settings.ejectionMethod()) {
                case FULL:
                    actual.put("evictionPolicy", "fullEviction");
                    break;
                case NRU:
                    actual.put("evictionPolicy", "nruEviction");
                    break;
                case NONE:
                    actual.put("evictionPolicy", "noEviction");
                    break;
                case VALUE:
                    break;
