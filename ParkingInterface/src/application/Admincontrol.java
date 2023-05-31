package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javafx.scene.control.CheckBox;
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
import main.account.Attendant;
import main.account.Employee;
import main.parkingspot.ParkingSpot;
import main.ticket.ParkingTicket;
import main.vehicle.Vehicle;

public class Admincontrol implements Initializable {

	private double x, y;
	private Connection connect;
	private Statement statement;
	private PreparedStatement prepare;
	private ResultSet result;
	private ObservableList<Attendant> employeeList;
	private ObservableList<ParkingSpot> spotList;

	@FXML
	private Button homeButton;
	@FXML
	private Button attendantButton;
	@FXML
	private Button floorButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button addAttendantButton;
	@FXML
	private Button updateAttendantButton;
	@FXML
	private Button removeAttendantButton;
	@FXML
	private Button clearAttendantButton;
	@FXML
	private TextField attendantIdInput;
	@FXML
	private TextField attendantFnameInput;
	@FXML
	private TextField attendantLnameInput;
	@FXML
	private TextField attendantEmailInput;
	@FXML
	private TextField attendantPhoneInput;
	@FXML
	private TextField attendantUsernameInput;
	@FXML
	private TextField attendantPasswordInput;
	@FXML
	private TextField attendantSearchInput;
	@FXML
	private TextField spotSearchInput;
	@FXML
	private AnchorPane homeForm;
	@FXML
	private AnchorPane attendantForm;
	@FXML
	private AnchorPane floorForm;
	@FXML
	private TableView<Attendant> employeeDataTable;
	@FXML
	private TableColumn<Attendant, String> employeeColId;
	@FXML
	private TableColumn<Attendant, String> employeeColFirstName;
	@FXML
	private TableColumn<Attendant, String> employeeColLastName;
	@FXML
	private TableColumn<Attendant, String> employeeColEmail;
	@FXML
	private TableColumn<Attendant, String> employeeColPhoneNum;
	@FXML
	private TableColumn<Attendant, String> employeeColUsername;
	@FXML
	private TableColumn<Attendant, String> employeeColPassword;
	@FXML
	private Button spotAddButton;
	@FXML
	private Button spotClearButton;
	@FXML
	private TextField spotNumberInput;
	@FXML
	private Button spotRemoveButton;
	@FXML
	private ComboBox<String> spotTypeInput;
	@FXML
	private ComboBox<String> floorNumberInput;
	@FXML
	private Button spotUpdateButton;
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
	private CheckBox showEmptySpotBox;
	@FXML
	private Label adminName;
	@FXML
	private Label totalAttendant;
	@FXML
	private Label totalSpot;
	@FXML
	private Label totalCarSpot;
	@FXML
	private Label totalMotorcycleSpot;
	@FXML
	private Label totalElectricSpot;

	public void switchForm(ActionEvent event) {

		if (event.getSource() == homeButton) {
			homeForm.setVisible(true);
			attendantForm.setVisible(false);
			floorForm.setVisible(false);
			displayTotalAttendant();
			displayTotalSpot();

		} else if (event.getSource() == attendantButton) {
			homeForm.setVisible(false);
			attendantForm.setVisible(true);
			floorForm.setVisible(false);
			employeeShowListData();
			employeeSearch();

		} else if (event.getSource() == floorButton) {
			homeForm.setVisible(false);
			attendantForm.setVisible(false);
			floorForm.setVisible(true);
			spotShowListData();
			spotSearch();

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

	public void employeeSearch() {

		FilteredList<Attendant> filter = new FilteredList<>(EmployeeListData(), e -> true);

		attendantSearchInput.textProperty().addListener((observableValue, oldValue, newValue) -> {

			filter.setPredicate(predicateEmployeeData -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String searchKey = newValue.toLowerCase();

				if (predicateEmployeeData.getId().toString().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getEmail().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getPhoneNum().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getUserName().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateEmployeeData.getPassword().toLowerCase().contains(searchKey)) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<Attendant> sortList = new SortedList<>(filter);

		sortList.comparatorProperty().bind(employeeDataTable.comparatorProperty());

		employeeDataTable.setItems(sortList);

	}

	public ObservableList<Attendant> EmployeeListData() {

		ObservableList<Attendant> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM EMPLOYEE WHERE EMP_POS = 'ATTENDANT'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			Employee employee;
			Attendant attendant;

			while (result.next()) {
				employee = new Employee(result.getString("EMP_NUM"), result.getString("EMP_FNAME"),
						result.getString("EMP_LNAME"), result.getString("EMP_EMAIL"), result.getString("EMP_PHONENUM"));
				attendant = new Attendant(result.getString("EMP_USERNAME"), result.getString("EMP_PASSWORD"), employee);
				listData.add(attendant);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public void employeeShowListData() {
		employeeList = EmployeeListData();

		employeeColId.setCellValueFactory(new PropertyValueFactory<>("id"));
		employeeColFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		employeeColLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		employeeColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		employeeColPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
		employeeColUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
		employeeColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

		employeeDataTable.setItems(employeeList);

	}

	public void addEmployee() {

		String sql = "INSERT INTO employee "
				+ "(EMP_NUM, EMP_FNAME, EMP_LNAME, EMP_EMAIL, EMP_PHONENUM, EMP_POS, EMP_USERNAME, EMP_PASSWORD) "
				+ "VALUES(?,?,?,?,?,'ATTENDANT',?,?)";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (attendantIdInput.getText().isEmpty() || attendantFnameInput.getText().isEmpty()
					|| attendantLnameInput.getText().isEmpty() || attendantPhoneInput.getText().isEmpty()
					|| attendantUsernameInput.getText().isEmpty() || attendantPasswordInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields");
				alert.showAndWait();
			} else {

				String checkId = "SELECT EMP_NUM FROM EMPLOYEE WHERE EMP_NUM = '" + attendantIdInput.getText() + "'";

				statement = connect.createStatement();
				result = statement.executeQuery(checkId);

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("ID: " + attendantIdInput.getText() + " already exists!");
					alert.showAndWait();
				} else {
					String checkUsername = "SELECT EMP_USERNAME FROM EMPLOYEE WHERE EMP_NUM = '"
							+ attendantIdInput.getText() + "'";

					statement = connect.createStatement();
					result = statement.executeQuery(checkUsername);

					if (result.next()) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Message");
						alert.setHeaderText(null);
						alert.setContentText("Username: " + attendantUsernameInput.getText() + " already exists!");
						alert.showAndWait();
					} else {

						prepare = connect.prepareStatement(sql);
						prepare.setString(1, attendantIdInput.getText());
						prepare.setString(2, attendantFnameInput.getText());
						prepare.setString(3, attendantLnameInput.getText());
						prepare.setString(4, attendantEmailInput.getText());
						prepare.setString(5, attendantPhoneInput.getText());
						prepare.setString(6, attendantUsernameInput.getText());
						prepare.setString(7, attendantPasswordInput.getText());

						prepare.executeUpdate();

						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Message");
						alert.setHeaderText(null);
						alert.setContentText("Successfully Added!");
						alert.showAndWait();

						employeeShowListData();
						employeeReset();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateEmployee() {

		String checkAttendant = "SELECT EMP_NUM from employee WHERE EMP_POS = 'ATTENDANT' AND EMP_NUM = '"
				+ attendantIdInput.getText() + "'";

		String sqlTest = "UPDATE employee SET ";

		if (!attendantFnameInput.getText().isEmpty()) {
			sqlTest += "EMP_FNAME = '" + attendantFnameInput.getText() + "', ";
		}
		if (!attendantLnameInput.getText().isEmpty()) {
			sqlTest += "EMP_LNAME = '" + attendantLnameInput.getText() + "', ";
		}
		if (!attendantPhoneInput.getText().isEmpty()) {
			sqlTest += "EMP_PHONENUM = '" + attendantPhoneInput.getText() + "', ";
		}
		if (!attendantEmailInput.getText().isEmpty()) {
			sqlTest += "EMP_EMAIL = '" + attendantEmailInput.getText() + "', ";
		}
		if (!attendantUsernameInput.getText().isEmpty()) {
			sqlTest += "EMP_USERNAME = '" + attendantUsernameInput.getText() + "', ";
		}
		if (!attendantPasswordInput.getText().isEmpty()) {
			sqlTest += "EMP_PASSWORD = '" + attendantPasswordInput.getText() + "', ";
		}
		sqlTest = sqlTest.substring(0, sqlTest.length() - 2) + " WHERE EMP_NUM ='" + attendantIdInput.getText() + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (attendantIdInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill the ID field");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkAttendant);

				if (!result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Attendant ID: " + attendantIdInput.getText() + " doesn't exists!");
					alert.showAndWait();
				} else {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Cofirmation Message");
					alert.setHeaderText(null);
					alert.setContentText(
							"Are you sure you want to UPDATE attendant ID: " + attendantIdInput.getText() + "?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {
						statement = connect.createStatement();
						statement.executeUpdate(sqlTest);
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Message");
						alert.setHeaderText(null);
						alert.setContentText("Successfully Updated!");
						alert.showAndWait();

						employeeShowListData();
						employeeReset();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteEmployee() {

		String sql = "DELETE FROM employee WHERE EMP_NUM = '" + attendantIdInput.getText() + "'";
		String checkAttendant = "SELECT EMP_NUM from employee WHERE EMP_POS = 'ATTENDANT' AND EMP_NUM = '"
				+ attendantIdInput.getText() + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {

			Alert alert;
			if (attendantIdInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill the ID field");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkAttendant);

				if (!result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Attendant ID: " + attendantIdInput.getText() + " doesn't exists!");
					alert.showAndWait();
				} else {

					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Cofirmation Message");
					alert.setHeaderText(null);
					alert.setContentText(
							"Are you sure you want to DELETE Attendant ID: " + attendantIdInput.getText() + "?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {
						statement = connect.createStatement();
						statement.executeUpdate(sql);

						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Message");
						alert.setHeaderText(null);
						alert.setContentText("Successfully Deleted!");
						alert.showAndWait();

						employeeShowListData();
						employeeReset();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void employeeSelect() {
		Attendant attendant = employeeDataTable.getSelectionModel().getSelectedItem();
		int num = employeeDataTable.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		attendantIdInput.setText(String.valueOf(attendant.getId()));
		attendantFnameInput.setText(attendant.getFirstName());
		attendantLnameInput.setText(attendant.getLastName());
		attendantPhoneInput.setText(attendant.getPhoneNum());
		attendantUsernameInput.setText(attendant.getUserName());
		attendantPasswordInput.setText(attendant.getPassword());
		attendantEmailInput.setText(attendant.getEmail());

	}

	public void employeeReset() {
		attendantIdInput.setText("");
		attendantFnameInput.setText("");
		attendantLnameInput.setText("");
		attendantEmailInput.setText("");
		attendantPhoneInput.setText("");
		attendantUsernameInput.setText("");
		attendantPasswordInput.setText("");
	}

	private String[] spotTypeList = { "Motorcycle", "Car", "Electric" };

	public void spotTypeList() {
		spotTypeInput.getItems().addAll(spotTypeList);
	}

	private String[] floorList = { "Floor 1", "Floor 2", "Floor 3" };

	public void floorList() {
		floorNumberInput.getItems().addAll(floorList);
	}

	public void spotSearch() {

		FilteredList<ParkingSpot> filter = new FilteredList<>(SpotListData(), e -> true);

		spotSearchInput.textProperty().addListener((observableValue, oldValue, newValue) -> {

			filter.setPredicate(predicateSpotData -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String searchKey = newValue.toLowerCase();

				if (predicateSpotData.getNumber().toString().contains(searchKey)) {
					System.out.println(predicateSpotData.getNumber());
					return true;
				} else if (predicateSpotData.getTicketId() != null
						&& predicateSpotData.getTicketId().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getFloorNum().toLowerCase().contains(searchKey)) {
					System.out.println(predicateSpotData.getFloorNum());
					return true;
				} else if (predicateSpotData.getLicenseNumber() != null
						&& predicateSpotData.getLicenseNumber().toLowerCase().contains(searchKey)) {
					return true;
				} else if (predicateSpotData.getType().toLowerCase().contains(searchKey)) {
					System.out.println(predicateSpotData.getType());
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

	public ObservableList<ParkingSpot> SpotListData() {

		ObservableList<ParkingSpot> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM ParkingSpot";

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

	public void spotShowListData() {
		spotList = SpotListData();

		spotColNum.setCellValueFactory(new PropertyValueFactory<>("number"));
		spotColType.setCellValueFactory(new PropertyValueFactory<>("type"));
		spotColVehnum.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
		spotColFloorNum.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
		spotColTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
		spotColParkDate.setCellValueFactory(new PropertyValueFactory<>("parkedDateString"));

		spotDataTable.setItems(spotList);

	}

	public void addSpot() {

		String sql = "INSERT INTO parkingspot " + "(SPOT_NUM, SPOT_TYPE, FLOOR_NUM) " + "VALUES(?,?,?)";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (spotNumberInput.getText().isEmpty() || spotTypeInput.getSelectionModel().getSelectedItem() == null
					|| floorNumberInput.getSelectionModel().getSelectedItem() == null) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields and select all options");
				alert.showAndWait();
			} else {

				String checkId = "SELECT SPOT_NUM FROM parkingspot WHERE SPOT_NUM = '" + spotNumberInput.getText()
						+ "'";

				statement = connect.createStatement();
				result = statement.executeQuery(checkId);

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Spot: " + spotNumberInput.getText() + " already exists!");
					alert.showAndWait();
				} else {

					prepare = connect.prepareStatement(sql);
					prepare.setString(1, spotNumberInput.getText());
					prepare.setString(2, (String) spotTypeInput.getSelectionModel().getSelectedItem());
					prepare.setString(3, (String) floorNumberInput.getSelectionModel().getSelectedItem());

					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Added!");
					alert.showAndWait();

					spotShowListData();
					spotReset();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateSpot() {

		String checkSpot = "SELECT SPOT_NUM from PARKINGSPOT WHERE SPOT_NUM = '" + spotNumberInput.getText() + "'";

		String checkSpotEmpty = "SELECT TICKET_ID from PARKINGSPOT WHERE SPOT_NUM = '" + spotNumberInput.getText()
				+ "'";

		String sqlTest = "UPDATE parkingspot SET ";

		if (spotTypeInput.getSelectionModel().getSelectedItem() != null) {
			sqlTest += "SPOT_TYPE = '" + spotTypeInput.getSelectionModel().getSelectedItem() + "', ";
		}
		if (floorNumberInput.getSelectionModel().getSelectedItem() != null) {
			sqlTest += "FLOOR_NUM = '" + floorNumberInput.getSelectionModel().getSelectedItem() + "', ";
		}
		sqlTest = sqlTest.substring(0, sqlTest.length() - 2) + " WHERE SPOT_NUM ='" + spotNumberInput.getText() + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (spotNumberInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill the Spot Number field");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkSpot);

				if (!result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Spot Number: " + spotNumberInput.getText() + " doesn't exists!");
					alert.showAndWait();
				} else {

					statement = connect.createStatement();
					result = statement.executeQuery(checkSpotEmpty);

					if (result.next()) {
						if (result.getString("TICKET_ID") != null) {
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Message");
							alert.setHeaderText(null);
							alert.setContentText("Spot Number: " + spotNumberInput.getText()
									+ " is currently occupied by a Vehicle!");
							alert.showAndWait();
						} else {

							alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Cofirmation Message");
							alert.setHeaderText(null);
							alert.setContentText(
									"Are you sure you want to UPDATE Spot Number: " + spotNumberInput.getText() + "?");
							Optional<ButtonType> option = alert.showAndWait();

							if (option.get().equals(ButtonType.OK)) {
								statement = connect.createStatement();
								statement.executeUpdate(sqlTest);
								alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Message");
								alert.setHeaderText(null);
								alert.setContentText("Successfully Updated!");
								alert.showAndWait();

								spotShowListData();
								spotReset();
							}
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteSpot() {

		String sql = "DELETE FROM parkingspot WHERE SPOT_NUM = '" + spotNumberInput.getText() + "'";

		String checkSpot = "SELECT SPOT_NUM from PARKINGSPOT WHERE SPOT_NUM = '" + spotNumberInput.getText() + "'";
		String checkSpotEmpty = "SELECT TICKET_ID from PARKINGSPOT WHERE SPOT_NUM = '" + spotNumberInput.getText()
				+ "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			Alert alert;
			if (spotNumberInput.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill the Spot Number field");
				alert.showAndWait();
			} else {

				statement = connect.createStatement();
				result = statement.executeQuery(checkSpot);

				if (!result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Spot Number: " + spotNumberInput.getText() + " doesn't exists!");
					alert.showAndWait();
				} else {

					statement = connect.createStatement();
					result = statement.executeQuery(checkSpotEmpty);

					if (result.next()) {
						if (result.getString("TICKET_ID") != null) {
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Message");
							alert.setHeaderText(null);
							alert.setContentText("Spot Number: " + spotNumberInput.getText()
									+ " is currently occupied by a Vehicle!");
							alert.showAndWait();
						} else {

							alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Cofirmation Message");
							alert.setHeaderText(null);
							alert.setContentText(
									"Are you sure you want to DELETE Spot Number: " + spotNumberInput.getText() + "?");
							Optional<ButtonType> option = alert.showAndWait();

							if (option.get().equals(ButtonType.OK)) {
								statement = connect.createStatement();
								statement.executeUpdate(sql);
								alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Message");
								alert.setHeaderText(null);
								alert.setContentText("Successfully Deleted!");
								alert.showAndWait();

								spotShowListData();
								spotReset();
							}
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void spotSelect() {
		ParkingSpot spot = spotDataTable.getSelectionModel().getSelectedItem();
		int num = spotDataTable.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		spotNumberInput.setText(String.valueOf(spot.getNumber()));

	}

	public void spotReset() {
		spotNumberInput.setText("");
		spotTypeInput.getSelectionModel().clearSelection();
		floorNumberInput.getSelectionModel().clearSelection();
	}

	public void showEmptySpot() {
		if (showEmptySpotBox.isSelected()) {
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

			spotColNum.setCellValueFactory(new PropertyValueFactory<>("number"));
			spotColType.setCellValueFactory(new PropertyValueFactory<>("type"));
			spotColVehnum.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
			spotColFloorNum.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
			spotColTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
			spotColParkDate.setCellValueFactory(new PropertyValueFactory<>("parkedDateString"));

			spotDataTable.setItems(listData);
		} else {
			spotShowListData();
		}
	}

	public void displayAdminName() {
		String getAdminName = "SELECT concat(EMP_FNAME, " + "' '"
				+ ", EMP_LNAME) AS EMP_NAME FROM employee WHERE EMP_USERNAME = '" + getData.username + "'";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(getAdminName);
			result = prepare.executeQuery();

			while (result.next()) {
				adminName.setText(result.getString("EMP_NAME"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayTotalAttendant() {
		String getAttendantCount = "SELECT count(*) as totalAttendant from employee";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(getAttendantCount);
			result = prepare.executeQuery();

			while (result.next()) {
				totalAttendant.setText(result.getString("totalAttendant"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayTotalSpot() {
		String getSpotCount = "SELECT " + "    count(*) AS totalSpot, "
				+ "    sum(case when SPOT_TYPE = 'Car' then 1 else 0 end) AS totalCarSpot, "
				+ "    sum(case when SPOT_TYPE = 'Motorcycle' then 1 else 0 end) AS totalMotorcycleSpot, "
				+ "	sum(case when SPOT_TYPE = 'Electric' then 1 else 0 end) AS totalElectricSpot " + "FROM parkingspot";

		DatabaseConnection connectNow = new DatabaseConnection();
		connect = connectNow.getConnection();

		try {
			prepare = connect.prepareStatement(getSpotCount);
			result = prepare.executeQuery();

			while (result.next()) {
				totalSpot.setText(result.getString("totalSpot"));
				totalCarSpot.setText(result.getString("totalCarSpot"));
				totalMotorcycleSpot.setText(result.getString("totalMotorcycleSpot"));
				totalElectricSpot.setText(result.getString("totalElectricSpot"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		displayAdminName();
		displayTotalAttendant();
		displayTotalSpot();
		employeeShowListData();
		spotShowListData();
		spotTypeList();
		floorList();
	}
}