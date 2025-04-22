            if (modelYear != 0)
                list.add(new TextPropertyDescriptor(MODEL_YEAR, "model year"));
            if (color != null)
                list.add(new ColorPropertyDescriptor(COLOR, "color"));
            if (manufacturer != null)
                list.add(new TextPropertyDescriptor(MANUFACTURER, "make"));
            if (model != null)
                list.add(new TextPropertyDescriptor(MODEL, "model"));
            if (engineSize != 0.0)
                list.add(new TextPropertyDescriptor(ENGINE_SIZE, "engine"));
