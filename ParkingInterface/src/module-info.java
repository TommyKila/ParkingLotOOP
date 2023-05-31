module ParkingInterface {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	opens main.account;
	opens main.parkingspot;
	opens application to javafx.graphics, javafx.fxml;
}
