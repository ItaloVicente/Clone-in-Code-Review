package org.eclipse.ui.tests.propertysheet;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.tests.api.SaveableMockViewPart;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;


public class PropertySheetAuto extends UITestCase {

    private class Car implements IPropertySource {
        private int modelYear = 0;

        private RGB color = null;

        private String manufacturer = null;

        private String model = null;

        private double engineSize = 0.0;

        private final static String prefix = "org.eclipse.ui.tests.standardcomponents.propertysheetauto.";

        private final static String MODEL_YEAR = prefix + "modelyear";

        private final static String COLOR = prefix + "color";

        private final static String MANUFACTURER = prefix + "manufacturer";

        private final static String MODEL = prefix + "model";

        private final static String ENGINE_SIZE = prefix + "enginesize";

        private IPropertyDescriptor[] descriptors;

        public Car(int carModelYear, RGB carColor, String carManufacturer,
                String carModel, double carEngineSize) {
            modelYear = carModelYear;
            color = carColor;
            manufacturer = carManufacturer;
            model = carModel;
            engineSize = carEngineSize;

            createDescriptors();
        }

        private void createDescriptors() {
            ArrayList list = new ArrayList(5);
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
            descriptors = (IPropertyDescriptor[]) list
                    .toArray(new IPropertyDescriptor[list.size()]);
        }

        @Override
		public Object getEditableValue() {
            return this;
        }

        @Override
		public IPropertyDescriptor[] getPropertyDescriptors() {
            return descriptors;
        }

        @Override
		public Object getPropertyValue(Object id) {
            if (id.equals(MODEL_YEAR))
                return Integer.toString(modelYear);
            if (id.equals(COLOR))
                return color;
            if (id.equals(MANUFACTURER))
                return manufacturer;
            if (id.equals(MODEL))
                return model;
            if (id.equals(ENGINE_SIZE))
                return Double.toString(engineSize);
            return null;
        }

        @Override
		public boolean isPropertySet(Object id) {
            return false;
        }

        @Override
		public void resetPropertyValue(Object id) {
            return;
        }

        @Override
		public void setPropertyValue(Object id, Object value) {
            if (id.equals(MODEL_YEAR))
                modelYear = new Integer((String) value).intValue();
            if (id.equals(COLOR))
                color = (RGB) value;
            if (id.equals(MANUFACTURER))
                manufacturer = (String) value;
            if (id.equals(MODEL))
                model = (String) value;
            if (id.equals(ENGINE_SIZE))
                engineSize = new Double((String) value).doubleValue();
        }

        @Override
		public String toString() {
            StringBuffer s = new StringBuffer();
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

    private void createCars() {
        cars = new Car[NUMBER_OF_CARS];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = createCar();
        }
    }

    private Car createCar() {
        int modelYear = 0;
        RGB color = null;
        String manufacturer = null;
        String model = null;
        double engineSize = 0.0;
        int FACTOR = 4;
        if (random.nextInt(FACTOR) < FACTOR - 1)
            modelYear = 1990 + random.nextInt(15);
        if (random.nextInt(FACTOR) < FACTOR - 1)
            color = new RGB(random.nextInt(256), random.nextInt(256), random
                    .nextInt(256));
        if (random.nextInt(FACTOR) < FACTOR - 1)
            manufacturer = makers[random.nextInt(makers.length)];
        if (random.nextInt(FACTOR) < FACTOR - 1)
            model = models[random.nextInt(models.length)];
        if (random.nextInt(FACTOR) < FACTOR - 1)
            engineSize = random.nextDouble() * 6;
        return new Car(modelYear, color, manufacturer, model, engineSize);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        workbenchWindow = openTestWindow();
        activePage = workbenchWindow.getActivePage();
        while (Display.getCurrent().readAndDispatch())
            ;
    }

    protected IWorkbenchPart createTestParts(IWorkbenchPage page)
            throws Throwable {
        IViewPart view = page.showView(IPageLayout.ID_PROP_SHEET);
        selectionProviderView = (SelectionProviderView) page
                .showView(SelectionProviderView.ID);
        while (Display.getCurrent().readAndDispatch())
            ;
        return view;

    }

    public void testInput() throws Throwable {
        PropertySheetPerspectiveFactory.applyPerspective(activePage); 
        PropertySheet propView = (PropertySheet) createTestParts(activePage);
        createCars();

        assertTrue("'Property' view should be visible", activePage.isPartVisible(propView));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));
                
        for (int i = 0; i < NUMBER_OF_SELECTIONS; i++) {
            int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
            ArrayList selection = new ArrayList(numberToSelect);
            while (selection.size() < numberToSelect) {
                int j = random.nextInt(NUMBER_OF_CARS);
                if (!selection.contains(cars[j]))
                    selection.add(cars[j]);
            }
            StructuredSelection structuredSelection = new StructuredSelection(
                    selection);
            selectionProviderView.setSelection(structuredSelection);
            while (Display.getCurrent().readAndDispatch())
                ;
            assertEquals(structuredSelection, propView.getShowInContext().getSelection());
        }
    }
    
    public void XtestInputIfHiddenBug69953() throws Throwable {
        PropertySheetPerspectiveFactory2.applyPerspective(activePage); 
        PropertySheet propView = (PropertySheet) createTestParts(activePage);
        createCars();
        
        assertFalse("'Property' view should be hidden", activePage.isPartVisible(propView));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));
        
        for (int i = 0; i < NUMBER_OF_SELECTIONS; i++) {
            int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
            ArrayList selection = new ArrayList(numberToSelect);
            while (selection.size() < numberToSelect) {
                int j = random.nextInt(NUMBER_OF_CARS);
                if (!selection.contains(cars[j]))
                    selection.add(cars[j]);
            }
            StructuredSelection structuredSelection = new StructuredSelection(
                    selection);
            selectionProviderView.setSelection(structuredSelection);
            while (Display.getCurrent().readAndDispatch())
                ;
            assertNull("Selection should be null in properties view", propView.getShowInContext()
                    .getSelection());            
        }
    }

    public void testInputIfHidden2Bug69953() throws Throwable {
        PropertySheetPerspectiveFactory3.applyPerspective(activePage); 
        PropertySheet propView = (PropertySheet) createTestParts(activePage);
        createCars();
        for (int i = 0; i < 10; i++) {
            IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);
            assertViewsVisibility1(propView, projectExplorer);

            activePage.activate(selectionProviderView);
            while (Display.getCurrent().readAndDispatch())
                ;
            
            int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
            ArrayList selection = new ArrayList(numberToSelect);
            while (selection.size() < numberToSelect) {
                int j = random.nextInt(NUMBER_OF_CARS);
                if (!selection.contains(cars[j]))
                    selection.add(cars[j]);
            }
            StructuredSelection structuredSelection = new StructuredSelection(
                    selection);
            selectionProviderView.setSelection(structuredSelection);
            while (Display.getCurrent().readAndDispatch())
                ;
            
            assertEquals(structuredSelection, propView.getShowInContext().getSelection());
            
            activePage.showView(IPageLayout.ID_PROP_SHEET);            
            assertViewsVisibility2(propView, projectExplorer);

            assertEquals(structuredSelection, propView.getShowInContext().getSelection());
        }
    }

    private void assertViewsVisibility1(PropertySheet propView, IViewPart projectExplorer) {
        while (Display.getCurrent().readAndDispatch())
            ;
        assertFalse("'Property' view should be hidden", activePage.isPartVisible(propView));
        assertTrue("'Project Explorer' view should be visible", activePage
                .isPartVisible(projectExplorer));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));
    }        
    
    private void assertViewsVisibility2(PropertySheet propView, IViewPart projectExplorer) {
        while (Display.getCurrent().readAndDispatch())
            ;
        assertTrue("'Property' view should be visible", activePage.isPartVisible(propView));
        assertFalse("'Project Explorer' view should be hidden", activePage
                .isPartVisible(projectExplorer));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));
    }        
        
    public void testSaveableRetargeting() throws Throwable {
        PropertySheetPerspectiveFactory.applyPerspective(activePage); 
    	IWorkbenchPart propView = createTestParts(activePage);
    	assertNull(propView.getAdapter(ISaveablePart.class));
    	IViewPart saveableView = activePage.showView(SaveableMockViewPart.ID);
    	activePage.activate(propView);
    	assertEquals(saveableView, propView.getAdapter(ISaveablePart.class));
    }
}

