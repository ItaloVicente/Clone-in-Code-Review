	public void testMHandledMenuItem_Check_Bug316752() {
		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		MMenu menu = MenuFactoryImpl.eINSTANCE.createMenu();
		MHandledMenuItem menuItem = MenuFactoryImpl.eINSTANCE
				.createHandledMenuItem();
		MCommand command = CommandsFactoryImpl.eINSTANCE.createCommand();
