package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;


public interface Player extends HockeyleagueObject {
	String getBirthplace();

	void setBirthplace(String value);

	int getNumber();

	void setNumber(int value);

	HeightKind getHeightMesurement();

	void setHeightMesurement(HeightKind value);

	int getHeightValue();

	void setHeightValue(int value);

	WeightKind getWeightMesurement();

	void setWeightMesurement(WeightKind value);

	int getWeightValue();

	void setWeightValue(int value);

	ShotKind getShot();

	void setShot(ShotKind value);

	String getBirthdate();

	void setBirthdate(String value);

} // Player
