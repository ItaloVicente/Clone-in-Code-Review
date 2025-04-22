            StringBuilder s = new StringBuilder();
            s.append("<");
            if (modelYear != 0) {
                s.append(modelYear);
                s.append(" ");
            }
            if (color != null) {
                s.append(color);
                s.append(" ");
            }
            if (manufacturer != null) {
                s.append(manufacturer);
                s.append(" ");
            }
            if (model != null) {
                s.append(model);
                s.append(" ");
            }
            if (engineSize != 0.0) {
                s.append(engineSize);
                s.append(" ");
            }
            s.append(">");
            return s.toString();
        }
    }

    private IWorkbenchPage activePage;

    private IWorkbenchWindow workbenchWindow;

    private SelectionProviderView selectionProviderView;

    private Car[] cars;

    private Random random = new Random();

    private static final int NUMBER_OF_CARS = 10;

    private static final int NUMBER_OF_SELECTIONS = 100;

    private static final String[] makers = new String[] { "Ford", "GM",
            "Chrysler", "BMW", "Toyota", "Nissan", "Honda", "Volvo" };

    private static final String[] models = new String[] { "Thunderbird",
            "Deville", "Viper", "320i", "Camry", "Ultima", "Prelude", "V70" };

    public PropertySheetAuto(String name) {
        super(name);
    }

    /**
     * Creates a array of car objects
     */
    private void createCars() {
        cars = new Car[NUMBER_OF_CARS];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = createCar();
        }
    }

    /**
     * Creates a car initialized with random values
     */
    private Car createCar() {
        int modelYear = 0;
        RGB color = null;
        String manufacturer = null;
        String model = null;
        double engineSize = 0.0;
        int FACTOR = 4;
        if (random.nextInt(FACTOR) < FACTOR - 1) {
