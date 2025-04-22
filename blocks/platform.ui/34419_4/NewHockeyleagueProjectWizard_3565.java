package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Arena;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.DefencePositionKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Forward;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ForwardPositionKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Goalie;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.GoalieStats;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HeightKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePlugin;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.League;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.PlayerStats;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ShotKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.WeightKind;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

public class NewHockeyleagueProjectWizard
	extends BasicNewResourceWizard {

	private WizardNewProjectCreationPage mainPage;

	private IWorkbench workbench;

	public void init(IWorkbench aWorkbench,
			IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		this.workbench = aWorkbench;
		setWindowTitle("New Hockey League Project"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		super.addPages();
		mainPage = new WizardNewProjectCreationPage("basicNewProjectPage");//$NON-NLS-1$
		mainPage.setTitle("Hockey League Example Project"); //$NON-NLS-1$
		mainPage.setDescription("Create a new Tabbed Properties View Hockey League Example Project"); //$NON-NLS-1$
		this.addPage(mainPage);
	}

	public boolean performFinish() {
		final IProject newProjectHandle = mainPage.getProjectHandle();
		IPath newPath = null;
		if (!mainPage.useDefaults())
			newPath = mainPage.getLocationPath();
		final IPath newProjectPath = newPath;
		final IFile[] newFile = new IFile[1];

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			protected void execute(IProgressMonitor monitor)
				throws CoreException {
				try {
					monitor.beginTask("Creating New Project", 3000);//$NON-NLS-1$

					monitor.setTaskName("Create a project descriptor");//$NON-NLS-1$
					monitor.worked(1000);
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					final IProjectDescription description = workspace
						.newProjectDescription(newProjectHandle.getName());
					description.setLocation(newProjectPath);

					monitor.setTaskName("Create the new project");//$NON-NLS-1$
					newProjectHandle.create(description,
						new SubProgressMonitor(monitor, 1000));

					if (monitor.isCanceled())
						throw new OperationCanceledException();

					monitor.setTaskName("Open the new project");//$NON-NLS-1$
					newProjectHandle
						.open(new SubProgressMonitor(monitor, 1000));

					if (monitor.isCanceled())
						throw new OperationCanceledException();

					monitor.setTaskName("Create the new file");//$NON-NLS-1$
					monitor.worked(1000);
					newFile[0] = newProjectHandle
						.getFile("example.hockeyleague");//$NON-NLS-1$
					ResourceSet resourceSet = new ResourceSetImpl();

					URI fileURI = URI.createPlatformResourceURI(newFile[0]
						.getFullPath().toString());

					Resource resource = resourceSet.createResource(fileURI);

					createInitialModel(resource);

					try {
						resource.save(Collections.EMPTY_MAP);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof CoreException) {
				ErrorDialog.openError(getShell(), "Creation Problems", //$NON-NLS-1$
					null, // no special message
					((CoreException) t).getStatus());
			} else {
				HockeyleaguePlugin.getPlugin().getLog().log(
					new Status(IStatus.ERROR, "HockeyleaguePlugin", 0, t//$NON-NLS-1$
						.toString(), t));
				MessageDialog.openError(getShell(), "Creation Problems", //$NON-NLS-1$
					MessageFormat.format("Internal error: {0}", //$NON-NLS-1$
						new Object[] {t.getMessage()}));
			}
			return false;
		}

		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		final IWorkbenchPart activePart = page.getActivePart();
		if (activePart instanceof ISetSelectionTarget) {
			final ISelection targetSelection = new StructuredSelection(
				newFile[0]);
			getShell().getDisplay().asyncExec(new Runnable() {

				public void run() {
					((ISetSelectionTarget) activePart)
						.selectReveal(targetSelection);
				}
			});
		}

		try {
			IDE.openEditor(page, newFile[0], true);
		} catch (PartInitException exception) {
			MessageDialog.openError(workbenchWindow.getShell(), "Open Editor",//$NON-NLS-1$
				exception.getMessage());
			return false;
		}

		return true;
	}

	private EObject createInitialModel(Resource resource) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;

		League league = hockeyleagueFactory.createLeague();
		league.setName("Global Hockey League");//$NON-NLS-1$
		league
			.setHeadoffice("99 Bee Street, Toronto, Ontario, Canada, M5J 1Y7");//$NON-NLS-1$
		resource.getContents().add(league);

		Arena arena = hockeyleagueFactory.createArena();
		arena.setAddress("40 Buy Street, Toronto, Ontario, Canada, M5J 1C3");//$NON-NLS-1$
		arena.setCapacity(18918);
		arena.setName("Storm Centre");//$NON-NLS-1$
		resource.getContents().add(arena);

		Team torontoStorm = hockeyleagueFactory.createTeam();
		torontoStorm.setName("Toronto Storm");//$NON-NLS-1$
		torontoStorm.setArena(arena);
		createPlayers(torontoStorm);
		resource.getContents().add(torontoStorm);

		arena = hockeyleagueFactory.createArena();
		arena
			.setAddress("1010 de La Gauchetiere Street, Montreal, Quebec, Canada, H3B B7B");//$NON-NLS-1$
		arena.setCapacity(22000);
		arena.setName("Global Centre");//$NON-NLS-1$
		resource.getContents().add(arena);

		Team montrealEagles = hockeyleagueFactory.createTeam();
		montrealEagles.setName("Montreal Eagles");//$NON-NLS-1$
		montrealEagles.setArena(arena);
		createPlayers(montrealEagles);
		resource.getContents().add(montrealEagles);

		arena = hockeyleagueFactory.createArena();
		arena.setAddress("1001 Atlas Drive, Ottawa, Ontario, Canada, K2V 1L8");//$NON-NLS-1$
		arena.setCapacity(19000);
		arena.setName("Ottawa Memorial Arena");//$NON-NLS-1$
		resource.getContents().add(arena);

		Team ottawaLions = hockeyleagueFactory.createTeam();
		ottawaLions.setName("Ottawa Lions");//$NON-NLS-1$
		ottawaLions.setArena(arena);
		createPlayers(ottawaLions);
		resource.getContents().add(ottawaLions);

		arena = hockeyleagueFactory.createArena();
		arena
			.setAddress("200 Griffins Way, Vancouver, British Columbia, Canada, V6B 9M2");//$NON-NLS-1$
		arena.setCapacity(18500);
		arena.setName("Century Place");//$NON-NLS-1$
		resource.getContents().add(arena);

		Team vancouverTigers = hockeyleagueFactory.createTeam();
		vancouverTigers.setName("Vancouver Tigers");//$NON-NLS-1$
		vancouverTigers.setArena(arena);
		createPlayers(vancouverTigers);
		resource.getContents().add(vancouverTigers);

		league.getTeams().add(torontoStorm);
		league.getTeams().add(montrealEagles);
		league.getTeams().add(ottawaLions);
		league.getTeams().add(vancouverTigers);
		return league;
	}

	private void createPlayers(Team team) {
		createGoalie(team);
		createLeftWing(team);
		createCenter(team);
		createRightWing(team);
		createRightDefence(team);
		createLeftDefence(team);
	}

	private void createRightDefence(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Defence defence = hockeyleagueFactory.createDefence();
		defence.setName("Josef Kraft");//$NON-NLS-1$
		defence.setBirthdate("September 2, 1980");//$NON-NLS-1$
		defence.setBirthplace("Plzen, Czechoslovakia");//$NON-NLS-1$
		defence.setHeightMesurement(HeightKind.INCHES_LITERAL);
		defence.setHeightValue(74);
		defence.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		defence.setWeightValue(224);
		defence.setNumber(15);
		defence.setShot(ShotKind.RIGHT_LITERAL);
		defence.setPosition(DefencePositionKind.RIGHT_DEFENCE_LITERAL);

		PlayerStats playerStats = hockeyleagueFactory.createPlayerStats();
		playerStats.setYear("2004");//$NON-NLS-1$
		playerStats.setTeam(team);
		playerStats.setGamesPlayedIn(67);
		playerStats.setGoals(28);
		playerStats.setAssists(63);
		playerStats.setPoints(91);
		playerStats.setPlusMinus(-25);
		playerStats.setPenaltyMinutes(43);
		playerStats.setPowerPlayGoals(14);
		playerStats.setShortHandedGoals(0);
		playerStats.setGameWinningGoals(0);
		playerStats.setShots(235);
		playerStats.setShotPercentage(11.9F);
		playerStats.setGameWinningGoals(4);
		defence.getPlayerStats().add(playerStats);

		team.getDefencemen().add(defence);
	}

	private void createLeftDefence(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Defence defence = hockeyleagueFactory.createDefence();
		defence.setName("Tom Orpik");//$NON-NLS-1$
		defence.setBirthdate("March 20, 1975");//$NON-NLS-1$
		defence.setBirthplace("Cayuga, Ontario, Canada");//$NON-NLS-1$
		defence.setHeightMesurement(HeightKind.INCHES_LITERAL);
		defence.setHeightValue(74);
		defence.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		defence.setWeightValue(224);
		defence.setNumber(15);
		defence.setShot(ShotKind.LEFT_LITERAL);
		defence.setPosition(DefencePositionKind.LEFT_DEFENCE_LITERAL);

		PlayerStats playerStats = hockeyleagueFactory.createPlayerStats();
		playerStats.setYear("2004");//$NON-NLS-1$
		playerStats.setTeam(team);
		playerStats.setGamesPlayedIn(67);
		playerStats.setGoals(28);
		playerStats.setAssists(63);
		playerStats.setPoints(91);
		playerStats.setPlusMinus(-25);
		playerStats.setPenaltyMinutes(43);
		playerStats.setPowerPlayGoals(14);
		playerStats.setShortHandedGoals(0);
		playerStats.setGameWinningGoals(0);
		playerStats.setShots(235);
		playerStats.setShotPercentage(11.9F);
		playerStats.setGameWinningGoals(4);
		defence.getPlayerStats().add(playerStats);

		team.getDefencemen().add(defence);
	}

	private void createCenter(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Forward forward = hockeyleagueFactory.createForward();
		forward.setName("Tom Orpik");//$NON-NLS-1$
		forward.setBirthdate("March 20, 1975");//$NON-NLS-1$
		forward.setBirthplace("Cayuga, Ontario, Canada");//$NON-NLS-1$
		forward.setHeightMesurement(HeightKind.INCHES_LITERAL);
		forward.setHeightValue(74);
		forward.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		forward.setWeightValue(224);
		forward.setNumber(15);
		forward.setShot(ShotKind.RIGHT_LITERAL);
		forward.setPosition(ForwardPositionKind.CENTER_LITERAL);

		PlayerStats playerStats = hockeyleagueFactory.createPlayerStats();
		playerStats.setYear("2004");//$NON-NLS-1$
		playerStats.setTeam(team);
		playerStats.setGamesPlayedIn(67);
		playerStats.setGoals(28);
		playerStats.setAssists(63);
		playerStats.setPoints(91);
		playerStats.setPlusMinus(-25);
		playerStats.setPenaltyMinutes(43);
		playerStats.setPowerPlayGoals(14);
		playerStats.setShortHandedGoals(0);
		playerStats.setGameWinningGoals(0);
		playerStats.setShots(235);
		playerStats.setShotPercentage(11.9F);
		playerStats.setGameWinningGoals(4);
		forward.getPlayerStats().add(playerStats);

		team.getForwards().add(forward);
	}

	private void createLeftWing(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Forward forward = hockeyleagueFactory.createForward();
		forward.setName("Chris Sargeant");//$NON-NLS-1$
		forward.setBirthdate("February 16, 1973");//$NON-NLS-1$
		forward.setBirthplace("Cayuga, Ontario, Canada");//$NON-NLS-1$
		forward.setHeightMesurement(HeightKind.INCHES_LITERAL);
		forward.setHeightValue(75);
		forward.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		forward.setWeightValue(198);
		forward.setNumber(22);
		forward.setShot(ShotKind.RIGHT_LITERAL);
		forward.setPosition(ForwardPositionKind.RIGHT_WING_LITERAL);

		PlayerStats playerStats = hockeyleagueFactory.createPlayerStats();
		playerStats.setYear("2004");//$NON-NLS-1$
		playerStats.setTeam(team);
		playerStats.setGamesPlayedIn(67);
		playerStats.setGoals(28);
		playerStats.setAssists(63);
		playerStats.setPoints(91);
		playerStats.setPlusMinus(-25);
		playerStats.setPenaltyMinutes(43);
		playerStats.setPowerPlayGoals(14);
		playerStats.setShortHandedGoals(0);
		playerStats.setGameWinningGoals(0);
		playerStats.setShots(235);
		playerStats.setShotPercentage(11.9F);
		playerStats.setGameWinningGoals(4);
		forward.getPlayerStats().add(playerStats);

		team.getForwards().add(forward);
	}

	private void createRightWing(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Forward forward = hockeyleagueFactory.createForward();
		forward.setName("Martin Smyth");//$NON-NLS-1$
		forward.setBirthdate("January 24, 1983");//$NON-NLS-1$
		forward.setBirthplace("Calgary, Alberta, Canada");//$NON-NLS-1$
		forward.setHeightMesurement(HeightKind.INCHES_LITERAL);
		forward.setHeightValue(71);
		forward.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		forward.setWeightValue(192);
		forward.setNumber(14);
		forward.setShot(ShotKind.LEFT_LITERAL);
		forward.setPosition(ForwardPositionKind.LEFT_WING_LITERAL);

		PlayerStats playerStats = hockeyleagueFactory.createPlayerStats();
		playerStats.setYear("2004");//$NON-NLS-1$
		playerStats.setTeam(team);
		playerStats.setGamesPlayedIn(80);
		playerStats.setGoals(45);
		playerStats.setAssists(35);
		playerStats.setPoints(80);
		playerStats.setPlusMinus(8);
		playerStats.setPenaltyMinutes(34);
		playerStats.setPowerPlayGoals(14);
		playerStats.setShortHandedGoals(0);
		playerStats.setGameWinningGoals(10);
		playerStats.setShots(229);
		playerStats.setShotPercentage(19.7F);
		playerStats.setGameWinningGoals(10);
		forward.getPlayerStats().add(playerStats);

		team.getForwards().add(forward);
	}

	private void createGoalie(Team team) {
		HockeyleagueFactory hockeyleagueFactory = HockeyleagueFactory.eINSTANCE;
		Goalie goalie = hockeyleagueFactory.createGoalie();
		goalie.setName("Jaugues Leblanc");//$NON-NLS-1$
		goalie.setBirthdate("July 7, 1979");//$NON-NLS-1$
		goalie.setBirthplace("Laval, Quebec, Canada");//$NON-NLS-1$
		goalie.setHeightMesurement(HeightKind.INCHES_LITERAL);
		goalie.setHeightValue(71);
		goalie.setWeightMesurement(WeightKind.POUNDS_LITERAL);
		goalie.setWeightValue(192);
		goalie.setNumber(29);
		goalie.setShot(ShotKind.LEFT_LITERAL);

		GoalieStats goalieStats = hockeyleagueFactory.createGoalieStats();
		goalieStats.setYear("2004");//$NON-NLS-1$
		goalieStats.setTeam(team);
		goalieStats.setGamesPlayedIn(67);
		goalieStats.setWins(39);
		goalieStats.setLosses(20);
		goalieStats.setTies(7);
		goalieStats.setMinutesPlayedIn(3943);
		goalieStats.setGoalsAgainst(142);
		goalieStats.setSaves(1591);
		goalieStats.setGoalsAgainstAverage(2.64F);
		goalieStats.setShutouts(4);
		goalieStats.setGoals(0);
		goalieStats.setAssists(1);
		goalieStats.setPoints(1);
		goalieStats.setPenaltyMinutes(6);
		goalieStats.setEmptyNetGoals(1);
		goalie.getGoalieStats().add(goalieStats);

		team.getGoalies().add(goalie);
	}
}
