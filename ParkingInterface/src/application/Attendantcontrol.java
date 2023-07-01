package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.parkingspot.ParkingSpot;
import main.ticket.ParkingTicket;
import main.vehicle.Vehicle;

public class Attendantcontrol implements Initializable {

	private double x, y;
	private Connection connect;
	private Statement statement;
	private PreparedStatement prepare;
	private ResultSet result;
	private ObservableList<ParkingSpot> spotList = FXCollections.observableArrayList();

	@FXML
	private Button entrancePanelButton;
	@FXML
	private Button exitPanelButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button printTicketButton;
	@FXML
	private Button scanTicketButton;
	@FXML
	private TableColumn<ParkingSpot, String> spotColFloorNum;
	@FXML
	private TableColumn<ParkingSpot, String> spotColNum;
	@FXML
	private TableColumn<ParkingSpot, String> spotColParkDate;
	@FXML
	private TableColumn<ParkingSpot, String> spotColType;
	@FXML
	private TableColumn<ParkingSpot, String> spotColVehnum;
	@FXML
	private TableColumn<ParkingSpot, String> spotColTicketId;
	@FXML
	private TableView<ParkingSpot> spotDataTable;
	@FXML
	private TableColumn<ParkingSpot, String> spotColFloorNum1;
	@FXML
	private TableColumn<ParkingSpot, String> spotColNum1;
	@FXML
	private TableColumn<ParkingSpot, String> spotColParkDate1;
	@FXML
	private TableColumn<ParkingSpot, String> spotColType1;
	@FXML
	private TableColumn<ParkingSpot, String> spotColVehnum1;
	@FXML
	private TableColumn<ParkingSpot, String> spotColTicketId1;
	@FXML
	private TableView<ParkingSpot> spotDataTable1;
	@FXML
	private AnchorPane entranceForm;
	@FXML
	private AnchorPane exitForm;
	@FXML
	private Label attendantName;
	@FXML
	private Label freeCarSpot;
	@FXML
	private Label freeElectricSpot;
	@FXML
	private Label freeMotorcycleSpot;
	@FXML
	private Label totalFees;
	@FXML
	private Label totalParkingTime;
	@FXML
	private TextField ticketIdInput;
	@FXML
	private TextField vehiclePlateExitInput;
	@FXML
	private TextField vehicleEntrancePlateInput;
	@FXML
	private TextField spotSearchInput;
	@FXML
	private TextField spotSearchInput1;
	@FXML
	private ComboBox<String> vehicleTypeInput;

	public void switchForm(ActionEvent event) {

		if (event.getSource() == entrancePanelButton) {
			entranceForm.setVisible(true);
			exitForm.setVisible(false);
			spotEntranceSearch();
			displaySpotCount();

		} else if (event.getSource() == exitPanelButton) {
			entranceForm.setVisible(false);
			exitForm.setVisible(true);
			spotExitSearch();
		}

	}

	public void logOut(ActionEvent evt) {

		try {

			logoutButton.getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);

			root.setOnMousePressed((MouseEvent event) -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});

			root.setOnMouseDragged((MouseEvent event) -> {
				stage.setX(event.getScreenX() - x);
				stage.setY(event.getScreenY() - y);

				stage.setOpacity(.8);
			});

			root.setOnMouseReleased((MouseEvent event) -> {
				stage.setOpacity(1);
			});

			stage.initStyle(StageStyle.TRANSPARENT);

			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void spotEntranceSearch() {

		FilteredList<ParkingSpot> filter = new FilteredList<>(FreeSpotListData(), e -> true);

		spotSearchInput.textProperty().addListener((observableValue, oldValue, newValue) -> {

			filter.setPredicate(predicateSpotData -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String searchKey = newValue.toLowerCase();

				if (predicateSpotData.getNumber().toString().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getTicketId() != null
						&& predicateSpotData.getTicketId().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getFloorNum().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getLicenseNumber() != null
						&& predicateSpotData.getLicenseNumber().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getType().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getParkedDateString() != null
						&& predicateSpotData.getParkedDateString().toLowerCase().contains(searchKey)) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<ParkingSpot> sortList = new SortedList<>(filter);

		sortList.comparatorProperty().bind(spotDataTable.comparatorProperty());
		spotDataTable.setItems(sortList);

	}

	public void spotExitSearch() {

		FilteredList<ParkingSpot> filter = new FilteredList<>(ParkedSpotListData(), e -> true);

		spotSearchInput1.textProperty().addListener((observableValue, oldValue, newValue) -> {

			filter.setPredicate(predicateSpotData -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String searchKey = newValue.toLowerCase();

				if (predicateSpotData.getNumber().toString().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getTicketId() != null
						&& predicateSpotData.getTicketId().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getFloorNum().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getLicenseNumber() != null
						&& predicateSpotData.getLicenseNumber().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getType().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getParkedDateString() != null
						&& predicateSpotData.getParkedDateString().toLowerCase().contains(searchKey)) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<ParkingSpot> sortList = new SortedList<>(filter);

		sortList.comparatorProperty().bind(spotDataTable1.comparatorProperty());
		spotDataTable1.setItems(sortList);

	}

	public ObservableList<ParkingSpot> ParkedSpotListData() {

		ObservableList<ParkingSpot> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM ParkingSpot WHERE TICKET_ID IS NOT NULL";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			ParkingSpot spot;
			Vehicle vehicle;
			ParkingTicket ticket;

			while (result.next()) {
				ticket = new ParkingTicket(result.getString("TICKET_ID"),
						result.getObject("PARKED_AT", LocalDateTime.class));
				vehicle = new Vehicle(result.getString("VEH_LIS_NUM"), result.getString("SPOT_TYPE"), ticket);
				spot = new ParkingSpot(result.getString("SPOT_NUM"), vehicle, result.getString("SPOT_TYPE"),
						result.getString("FLOOR_NUM"));
				listData.add(spot);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public ObservableList<ParkingSpot> FreeSpotListData() {

		ObservableList<ParkingSpot> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM ParkingSpot WHERE TICKET_ID IS NULL";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			ParkingSpot spot;
			Vehicle vehicle;
			ParkingTicket ticket;

			while (result.next()) {
				ticket = new ParkingTicket(result.getString("TICKET_ID"),
						result.getObject("PARKED_AT", LocalDateTime.class));
				vehicle = new Vehicle(result.getString("VEH_LIS_NUM"), result.getString("SPOT_TYPE"), ticket);
				spot = new ParkingSpot(result.getString("SPOT_NUM"), vehicle, result.getString("SPOT_TYPE"),
						result.getString("FLOOR_NUM"));
				listData.add(spot);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public void spotEntranceShowListData() {
		spotList = FreeSpotListData();

		spotColNum.setCellValueFactory(new PropertyValueFactory<>("number"));
		spotColType.setCellValueFactory(new PropertyValueFactory<>("type"));
		spotColVehnum.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
		spotColFloorNum.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
		spotColTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
		spotColParkDate.setCellValueFactory(new PropertyValueFactory<>("parkedDateString"));

		spotDataTable.setItems(spotList);

	}

	public void spotExitShowListData() {
		spotList = ParkedSpotListData();

		spotColNum1.setCellValueFactory(new PropertyValueFactory<>("number"));
		spotColType1.setCellValueFactory(new PropertyValueFactory<>("type"));
		spotColVehnum1.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
		spotColFloorNum1.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
		spotColTicketId1.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
		spotColParkDate1.setCellValueFactory(new PropertyValueFactory<>("parkedDateString"));

		spotDataTable1.setItems(spotList);

	}

	public void displayAttendantName() {
		String getAttendantName = "SELECT concat(EMP_FNAME, " + "' '"
				+ ", EMP_LNAME) AS EMP_NAME FROM employee WHERE EMP_USERNAME = '" + getData.username + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(getAttendantName);
			result = prepare.executeQuery();

			while (result.next()) {
				attendantName.setText(result.getString("EMP_NAME"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] vehicleTypeList = { "Motorcycle", "Car", "Electric" };

	public void vehicleTypeList() {
		vehicleTypeInput.getItems().addAll(vehicleTypeList);
	}

	public void printTicket() {

		LocalDateTime now = LocalDateTime.now();

		String sql = "UPDATE PARKINGSPOT SET VEH_LIS_NUM = '" + vehicleEntrancePlateInput.getText() + "', PARKED_AT = '"
				+ now.toString().replace("T", " ") + "' , TICKET_ID = (SELECT SPOT_NUM"
				+ "  FROM (SELECT * FROM PARKINGSPOT) AS A "
				+ "  WHERE spot_num IN (SELECT MIN(SPOT_NUM) FROM (SELECT * FROM PARKINGSPOT) AS B  WHERE TICKET_ID IS NULL AND SPOT_TYPE = '"
				+ vehicleTypeInput.getSelectionModel().getSelectedItem() + "')) WHERE SPOT_NUM = (" + "SELECT SPOT_NUM"
				+ "  FROM (SELECT * FROM PARKINGSPOT) AS A "
				+ "  WHERE spot_num IN (SELECT MIN(SPOT_NUM) FROM (SELECT * FROM PARKINGSPOT) AS B  WHERE TICKET_ID IS NULL AND SPOT_TYPE = '"
				+ vehicleTypeInput.getSelectionModel().getSelectedItem() + "'))";

		String checkLisNum = "SELECT VEH_LIS_NUM from PARKINGSPOT WHERE VEH_LIS_NUM = '" + vehicleEntrancePlateInput.getText()
				+ "'";

		String checkEmptySpot = "select SPOT_NUM FROM PARKINGSPOT WHERE TICKET_ID IS NULL AND SPOT_TYPE = '"
				+ vehicleTypeInput.getSelectionModel().getSelectedItem() + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (vehicleEntrancePlateInput.getText().isEmpty() || vehicleTypeInput.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all information fields");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkLisNum);

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Vehicle with License Plate Number: " + vehicleEntrancePlateInput.getText()
							+ " already exists within the Parking Lot!");
					alert.showAndWait();

				} else {
					statement = connect.createStatement();
					result = statement.executeQuery(checkEmptySpot);
					if (!result.next()) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Message");
						alert.setHeaderText(null);
						alert.setContentText("There are not any empty spot for vehicle type: "
								+ vehicleTypeInput.getSelectionModel().getSelectedItem());
						alert.showAndWait();
					} else {

						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Cofirmation Message");
						alert.setHeaderText(null);
						alert.setContentText("Are you sure you want to park Vehicle with Plate Number: "
								+ vehicleEntrancePlateInput.getText() + "?");
						Optional<ButtonType> option = alert.showAndWait();

						if (option.get().equals(ButtonType.OK)) {
							statement = connect.createStatement();
							statement.executeUpdate(sql);
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Message");
							alert.setHeaderText(null);
							alert.setContentText("Successfully Parked!");
							alert.showAndWait();

							spotEntranceShowListData();
							spotExitShowListData();

							displaySpotCount();
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void scanTicket() {

		String sql = "UPDATE PARKINGSPOT SET VEH_LIS_NUM = NULL, PARKED_AT = NULL, TICKET_ID = NULL WHERE VEH_LIS_NUM = '"
				+ vehiclePlateExitInput.getText() + "'";

		String checkTicket = "SELECT * FROM parkingspot WHERE VEH_LIS_NUM = '" + vehiclePlateExitInput.getText()
				+ "' AND TICKET_ID = '" + ticketIdInput.getText() + "'";

		String checkLisNum = "SELECT VEH_LIS_NUM from PARKINGSPOT WHERE VEH_LIS_NUM = '"
				+ vehiclePlateExitInput.getText() + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (vehiclePlateExitInput.getText().isEmpty() || ticketIdInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all information fields");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkLisNum);

				if (!result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Vehicle with License Plate Number: " + vehiclePlateExitInput.getText()
							+ " does not exists within the Parking Lot!");
					alert.showAndWait();
				} else {

					statement = connect.createStatement();
					result = statement.executeQuery(checkTicket);

					if (!result.next()) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Message");
						alert.setHeaderText(null);
						alert.setContentText("Vehicle with License Plate Number: " + vehiclePlateExitInput.getText()
								+ " does not match with the Ticket ID: " + ticketIdInput.getText() + "!");
						alert.showAndWait();
					} else {

						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Cofirmation Message");
						alert.setHeaderText(null);
						alert.setContentText("Are you sure you want to scan Vehicle with Plate Number: "
								+ vehiclePlateExitInput.getText() + " 's Ticket?");
						Optional<ButtonType> option = alert.showAndWait();

						if (option.get().equals(ButtonType.OK)) {
							getParkingFees();
							statement = connect.createStatement();
							statement.executeUpdate(sql);
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Message");
							alert.setHeaderText(null);
							alert.setContentText("Successfully Scanned!");
							alert.showAndWait();

							spotEntranceShowListData();
							spotExitShowListData();
							displaySpotCount();
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getParkingFees() {

		LocalDateTime now = LocalDateTime.now();
		LocalDate dateNow = LocalDate.now();

		String getParkedAt = "SELECT PARKED_AT FROM PARKINGSPOT WHERE VEH_LIS_NUM = '" + vehiclePlateExitInput.getText()
				+ "'";

		String getHourlyFee = "select HOURLY_FEE from parkingfee";

		try {
			prepare = connect.prepareStatement(getParkedAt);
			result = prepare.executeQuery();

			while (result.next()) {

				Duration duration = Duration.between(now, result.getObject("PARKED_AT", LocalDateTime.class));

				double timeParked = Math.abs(duration.getSeconds()) / 3600.0;
				long seconds = Math.abs(duration.getSeconds());
				long hours = seconds / 3600;
				seconds -= (hours * 3600);
				long minutes = seconds / 60;
				seconds -= (minutes * 60);

				totalParkingTime.setText(hours + "h:" + minutes + "m:" + seconds + "s");

				prepare = connect.prepareStatement(getHourlyFee);
				result = prepare.executeQuery();

				while (result.next()) {
					int fees = (int) Math.round(result.getInt("HOURLY_FEE") * timeParked);
					totalFees.setText(fees + "đ");

					String updateTotalCash = "INSERT INTO CASHEARNED VALUES (" + fees + ", '"
							+ dateNow.toString().replace("T", " ") + "')";
					statement = connect.createStatement();
					statement.executeUpdate(updateTotalCash);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void displaySpotCount() {
		String getSpotCount = "SELECT " + "    count(*) AS totalSpot, "
				+ "    sum(case when SPOT_TYPE = 'Car' and TICKET_ID IS NULL then 1 else 0 end) AS freeCarSpot, "
				+ "    sum(case when SPOT_TYPE = 'Motorcycle' and TICKET_ID IS NULL then 1 else 0 end) AS freeMotorcycleSpot, "
				+ "	sum(case when SPOT_TYPE = 'Electric' and TICKET_ID IS NULL then 1 else 0 end) AS freeElectricSpot "
				+ "FROM parkingspot";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(getSpotCount);
			result = prepare.executeQuery();

			while (result.next()) {
				freeCarSpot.setText(result.getString("freeCarSpot"));
				freeMotorcycleSpot.setText(result.getString("freeMotorcycleSpot"));
				freeElectricSpot.setText(result.getString("freeElectricSpot"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetInput() {
		vehiclePlateExitInput.setText("");
		ticketIdInput.setText("");
		totalParkingTime.setText("");
		totalFees.setText("");
	}

	public void spotSelect() {
		ParkingSpot spot = spotDataTable1.getSelectionModel().getSelectedItem();
		int num = spotDataTable1.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		vehiclePlateExitInput.setText(String.valueOf(spot.getLicenseNumber()));
		ticketIdInput.setText(String.valueOf(spot.getTicketId()));

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		spotEntranceShowListData();
		spotExitShowListData();
		displayAttendantName();
		displaySpotCount();
		vehicleTypeList();
		spotEntranceSearch();
		spotExitSearch();
	}
}
