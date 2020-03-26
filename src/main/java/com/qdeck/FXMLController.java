package com.qdeck;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;
import models.BasicConnectionPool;
import models.transitions.FadeOutUpBigTransition;
import models.transitions.FlashTransition;
import models.transitions.ShakeTransition;
import models.ticket;
import models.transitions.FadeInDownTransition;

public class FXMLController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="Global Variables">
    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    String staffNo = "";
    Object selection;
    String tkt;
    private String staff_level;
    private String currentCusTag;
    private String newCusTag;
    private String othersTag;
    private String serviceTag;
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);
    
    //static ConnectionPool connectionPool = BasicConnectionPool.create(url, username, password);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML Label currentCusNoInline;
    @FXML Label servicesNoInline;
    @FXML Label newCusNoInline;
    @FXML Label otherNoInline;
    @FXML private Label productivityLabel;
    @FXML public AnchorPane containerPane;
    @FXML private AnchorPane currentCustomerNode; 
    @FXML public ListView<ticket> currentCusAllListView;
    @FXML private Label currentCustomerTranferNoLabel;
    @FXML private CheckBox currentCusAutoTransferCB;
    @FXML private Label currentCusCurrentlyServingLabel;
    @FXML private ToggleButton currentCusLockBtn;
    @FXML public ListView<ticket> newCusAllListView;
    @FXML private Label newCustomerTranferNoLabel;
    @FXML private CheckBox newCusAutoTransferCB;
    @FXML private Label newCusCurrentlyServingLabel;
    @FXML private ToggleButton newCusLockBtn;
    @FXML public ListView<ticket> servicesAllListView;
    @FXML private Label servicesTranferNoLabel;
    @FXML private CheckBox servicesAutoTransferCB;
    @FXML private Label servicesCurrentlyServingLabel;
    @FXML private ToggleButton servicesLockBtn;
    @FXML public ListView<ticket> otherAllListView;
    @FXML private Label otherTranferNoLabel;
    @FXML private CheckBox otherAutoTransferCB;
    @FXML private Label otherCurrentlyServingLabel;
    @FXML private ToggleButton otherLockBtn;
    @FXML private MenuBar menuBar;
    @FXML private StackPane cardsStackPane;
    @FXML private AnchorPane loginNode;
    @FXML private Button loginBtn;
    @FXML private PasswordField passwordTextField;
    @FXML TextField staffNoTextField;
    @FXML ComboBox<String> loginCombo;
    @FXML private AnchorPane newCustomerNode;
    @FXML private AnchorPane otherNode;
    @FXML private AnchorPane servicesNode;
    @FXML private AnchorPane statsNode;
    @FXML private MenuItem currentCusMenuItem;
    @FXML private MenuItem newCusMenuItem;
    @FXML private MenuItem servicesMenuItem;
    @FXML private MenuItem otherMenuItem;
    @FXML private Label loginActionLabel;
    @FXML public Label loggedInAsLabel;
    @FXML private CategoryAxis barChatCategoryAxis;
    @FXML private NumberAxis barChstNumberAxis;
    @FXML private BarChart<?,?> statsBarChart;
    @FXML private LineChart<?, ?> lineChart;
    @FXML private PieChart statsPieChart;
    @FXML private Label avgServiceTimeLabel;
    @FXML private Label avgWaitTimeLabel;
    @FXML private Label counterLabel;
    @FXML private Label peopleAttentedTO;
    @FXML Label currentCusMissedNoLabel;
    @FXML Label newCusMissedNoLabel;
    @FXML Label otherMissedNoLabel;
    @FXML Label servicesMissedNoLabel;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Action Methods">
    public void close(){
    Platform.runLater(() -> {
        try{
            if(loginCombo.getSelectionModel().getSelectedItem() != null){
                switch (loginCombo.getSelectionModel().getSelectedItem()) {
                        case "Current Customer":
                            addTimeDone(currentCusAllListView,currentCusTag);
                            break;
                        case "Others":
                            addTimeDone(otherAllListView, othersTag);
                            break;
                        case "New Customer":
                            addTimeDone(newCusAllListView, newCusTag);
                            break;
                        case "Services":
                            addTimeDone(servicesAllListView, serviceTag);
                            break;
                  }
            }
            Connection con=pool.getConnection();
            Statement stmt =con.createStatement();
            stmt.executeUpdate("update staff set online = '0' where staff_no = '"+staffNoTextField.getText()+"'");
            System.out.println(staffNoTextField.getText());
            System.out.println("done");
            pool.releaseConnection(con);
        }catch(SQLException e){e.printStackTrace();}  
        }); 
    }
    void createAlert(AlertType alertType, String title, String contextText, String headerText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
        ShakeTransition s = new ShakeTransition(alert.getDialogPane());
        s.play();
    }
    Optional<Pair<String, String>> transferChoiseDialogWithServices(){
        
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Transfer Ticket");
        dialog.setHeaderText("Select queue and service to transfer to");
        dialog.setGraphic(new ImageView(this.getClass().getResource("/iconsAndLogos/icons8-exchange-802.png").toString()));
        ButtonType done = new ButtonType("Transfer", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 200, 10, 10));

        ComboBox queueSelection = new ComboBox();
        ComboBox servicesSelction = new ComboBox();

        grid.add(queueSelection, 1, 0);
        grid.add(servicesSelction, 1, 1);
        dialog.getDialogPane().setContent(grid);

        String lcb = loginCombo.getSelectionModel().getSelectedItem();
        try{
            Connection con=pool.getConnection();
            ArrayList<String> ql = new ArrayList<>();
            PreparedStatement getqueue = con.prepareStatement("select service from services where service != '"+lcb+"'");
            ResultSet gq = getqueue.executeQuery();
            queueSelection.getItems().clear();
            while(gq.next()){
                String temp = gq.getString("service");
                queueSelection.getItems().addAll(temp);
                ql.add(temp);
            }
            queueSelection.setPromptText("Select Queue");
            servicesSelction.setPromptText("Select Service");
            queueSelection.setOnAction((Event t) -> {
                for (int i = 0; i < ql.size(); i++) {
                    if(queueSelection.getSelectionModel().getSelectedItem().toString().equals(ql.get(i))){
                        try {
                            servicesSelction.getItems().clear();
                            PreparedStatement getservices = con.prepareStatement("select service from queue_services where q_no = (select s_no from services where service = '"+ql.get(i)+"')");
                            ResultSet gs = getservices.executeQuery();
                            while(gs.next()){
                                servicesSelction.getItems().addAll(gs.getString("service"));
                            }
                            servicesSelction.getSelectionModel().selectFirst();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == done){ 
                    if(queueSelection.getSelectionModel().getSelectedItem().toString() == null) {
                        createAlert(AlertType.ERROR, "Error", "Cannot Transfer Ticket", "Please select a queue and service to transfer to");
                    } else { 
                        try {
                            ArrayList<String> queue_list = new ArrayList<>();
                            ArrayList<String> service_list = new ArrayList<>();
                            PreparedStatement getqueue2 = con.prepareStatement("select service from services");
                            PreparedStatement getservices = con.prepareStatement("select service from queue_services");
                            ResultSet gq2 = getqueue2.executeQuery();
                            ResultSet gs = getservices.executeQuery();
                            while(gq2.next()){
                                queue_list.add(gq2.getString("service"));
                            }
                            while(gs.next()){
                                service_list.add(gs.getString("service"));
                            }
                            for (int i = 0; i < queue_list.size(); i++) {
                                if(queueSelection.getSelectionModel().getSelectedItem().toString().equals(queue_list.get(i))){
                                    for (int j = 0; i<service_list.size(); j++){
                                        if(servicesSelction.getSelectionModel().getSelectedItem().toString().equals(service_list.get(j))){
                                            return new Pair<>(queue_list.get(i), service_list.get(j));
                                        }
                                    }
                                }
                            }
                            pool.releaseConnection(con);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                return null;
            });
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.orElse(null) == null){
            return null;
        }
        else{
            return result;
        }
    }
    void showMissedTickets(Label missedNoLabel, String tag){
    try{
        Connection con = pool.getConnection();
        PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and tag = '"+tag+"' and staff_no = '"+staffNoTextField.getText()+"'");
        ResultSet counter = count.executeQuery();
        while(counter.next()){
            String value = counter.getString("count(*)");
            missedNoLabel.setText(value);
        }
        pool.releaseConnection(con);
    }
    catch(SQLException e){e.printStackTrace();}
}
    public void callTicketToDisplay(String tToC){
        try{
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet staff_counter = stmt.executeQuery("select counter from staff where staff_no = '"+staffNoTextField.getText()+"'");
            String counter = "";
            while(staff_counter.next()){counter = staff_counter.getString("counter");}
            System.out.println(tToC +" Goto Counter "+ counter);
            pool.releaseConnection(con);
        }
        catch(SQLException e){}
        }
    public void callTicketToDisplay(Deque<ticket> q){
        try{
            Connection con=pool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet staff_counter = stmt.executeQuery("select counter from staff where staff_no = '"+staffNoTextField.getText()+"'");
            String counter = "";
            while(staff_counter.next()){counter = staff_counter.getString("counter");}
            System.out.println(q.peek().getTag()+q.peek().getTicketNumber() +" Goto Counter "+ counter);
            pool.releaseConnection(con);
        }
        catch(SQLException e){}
        }
    public void nextButtonActionToPerform(ListView allListVIew, CheckBox autoTransferCB, Label currentlyServingLabel, String tag){
        try{
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            //first lock the ticket
            stmt.executeUpdate("update tickets set locked = true, staff_no = '"+staffNoTextField.getText()+"' where time_called is null and locked is null and missing_client is null and tag='"+tag+"' limit 1");
            stmt.executeUpdate("update transfer set locked = true, staff_no = '"+staffNoTextField.getText()+"' where time_called is null and locked is null and trans_to='"+tag+"' limit 1");
            //now get the ticket
            PreparedStatement call_next_ticket = con.prepareStatement("select tag,t_no,service from tickets where time_called is null and locked is not null and missing_client is null and tag='"+tag+"' and staff_no = '"+staffNoTextField.getText()+"' limit 1");
            PreparedStatement trans_tickets = con.prepareStatement("select tag, t_no from transfer where time_called is null and locked is not null and staff_no = '"+staffNoTextField.getText()+"' and trans_to = '"+tag+"' limit 1");
            ResultSet rs = call_next_ticket.executeQuery();
            ResultSet rs2 = trans_tickets.executeQuery();
            String localtime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            if(!allListVIew.getItems().isEmpty()){
                if(allListVIew.getItems().size()>2)
                    allListVIew.getItems().remove(0);
                String vtkt = allListVIew.getItems().get(allListVIew.getItems().size()-1).toString();
                String vtag = vtkt.substring(0, 1);
                int vtn = Integer.valueOf(vtkt.substring(1,vtkt.indexOf(" ")));
                if(vtag.equals(tag)){
                    stmt.executeUpdate("update tickets set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null");
                }
                else{
                    stmt.executeUpdate("update transfer set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null");
                }
            }
            if(rs.next()){
                if(autoTransferCB.isSelected()) {
                    if(rs2.next()){
                        String vtag = rs2.getString("tag");
                        int vt_no = rs2.getInt("t_no");
                        tkt = vtag + vt_no;
                        PreparedStatement getService = con.prepareStatement("select service from transfer where tag = '"+vtag+"' and t_no = '"+vt_no+"'");
                        ResultSet gs = getService.executeQuery();
                        String service = "";
                        while(gs.next()){service = gs.getString("service");}
                        stmt.executeUpdate("update transfer set time_called = '"+localtime+"', staff_no = '"+staffNoTextField.getText()+"' where tag = '"+vtag+"' and t_no = '"+vt_no+"' and time_called is null");
                        allListVIew.getItems().add(new ticket(vt_no, vtag) + " - " + service);
                        currentlyServingLabel.setText(tkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(tkt);           
                        stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '"+staffNoTextField.getText()+"' and tag='"+tag+"' limit 1");                       
                    }
                    else{
                        String ntag = rs.getString("tag");
                        int ntn = rs.getInt("t_no");
                        String service = rs.getString("service");
                        String ntkt = ntag+ntn;
                        stmt.executeUpdate("update tickets set time_called = '"+localtime+"', staff_no = '"+staffNoTextField.getText()+"' where t_no = '"+ntn+"' and tag = '"+ntag+"' and time_called is null");
                        allListVIew.getItems().add(new ticket(ntn, ntag) + " - " + service);
                        currentlyServingLabel.setText(ntkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(ntkt);
                        stmt.executeUpdate("update transfer set locked = null, staff_no = null where time_called is null and locked is not null and staff_no = '"+staffNoTextField.getText()+"' and trans_to='"+tag+"' limit 1");
                    }
                }
                else if(!autoTransferCB.isSelected()){
                    String ntag = rs.getString("tag");
                    int ntn = rs.getInt("t_no");
                    String service = rs.getString("service");
                    String ntkt = ntag+ntn;
                    stmt.executeUpdate("update tickets set time_called = '"+localtime+"', staff_no = '"+staffNoTextField.getText()+"' where t_no = '"+ntn+"' and tag = '"+ntag+"' and time_called is null");
                    allListVIew.getItems().add(new ticket(ntn, ntag)  + " - " + service);
                    currentlyServingLabel.setText(ntkt);
                    flash(currentlyServingLabel);
                    callTicketToDisplay(ntkt);
                    stmt.executeUpdate("update transfer set locked = null, staff_no = null where time_called is null and locked is not null and staff_no = '"+staffNoTextField.getText()+"' and trans_to='"+tag+"' limit 1");
                }
            }
            else if (rs2.next()){
                if(autoTransferCB.isSelected()) {
                    String vtag = rs2.getString("tag");
                    int vt_no = rs2.getInt("t_no");
                    tkt = vtag + vt_no;
                    PreparedStatement getService = con.prepareStatement("select service from transfer where tag = '"+vtag+"' and t_no = '"+vt_no+"'");
                    ResultSet gs = getService.executeQuery();
                    String service = "";
                    while(gs.next()){service = gs.getString("service");}
                    stmt.executeUpdate("update transfer set time_called = '"+localtime+"', staff_no = '"+staffNoTextField.getText()+"' where tag = '"+vtag+"' and t_no = '"+vt_no+"' and time_called is null");
                    allListVIew.getItems().add(new ticket(vt_no, vtag)  + " - " + service);
                    currentlyServingLabel.setText(tkt);
                    flash(currentlyServingLabel);
                    callTicketToDisplay(tkt);                       
                    stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '"+staffNoTextField.getText()+"' and tag='"+tag+"' limit 1");                       
                }
                else{createAlert(AlertType.WARNING, "Empty Queue", "Check auto call transfer box to call transfered ticket automatically", null);}
            }
            else{createAlert(AlertType.WARNING, "Empty Queue", "Queue is empty", null);}
            pool.releaseConnection(con);
        }
        catch(Exception e){e.printStackTrace();}

    }
    void transferTicket(String vtag, String vtn, String tag, String service){
        try{
            Connection connect=pool.getConnection();
            Statement stmt=connect.createStatement();
            String tr1 = vtag;
            String tr2 = vtn;
            String tr3 = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            String cmd = "insert into transfer (tag,t_no, trans_to, time_trans, service) values('"+tr1+"','"+tr2+"', '"+tag+"','"+tr3+"','"+service+"')";
            stmt.executeUpdate(cmd);
            String updTransferred = "update tickets set time_done = '"+tr3+"', transferred = TRUE where tag = '"+tr1+"' and t_no = '"+tr2+"' ";
            stmt.executeUpdate(updTransferred);
            System.out.println("Transfer Table Updated");
            pool.releaseConnection(connect);
    }
    catch(SQLException ex){ex.printStackTrace();}
    }
    public void transferButtonActionToPerform(ListView allListView){
    try{
         if(!allListView.getItems().isEmpty()){
        String v = allListView.getItems().get(allListView.getItems().size() -1).toString();
        if(v != null){
            String vtag = v.substring(0,1);
            String vtn = v.substring(1,v.indexOf(" "));
            String vticket = vtag+vtn;
            String time_done = "";
            Connection connect = pool.getConnection();
            PreparedStatement locstmt = connect.prepareStatement("select time_done from tickets where tag = '"+vtag+"' and t_no = '"+vtn+"' limit 1");
            ResultSet rs = locstmt.executeQuery();
            while(rs.next()){
                time_done = rs.getString("time_done");
            }
            if(time_done  == null){
                Optional<Pair<String,String>> transferPair = transferChoiseDialogWithServices();
                if(transferPair.get().getKey() != null){
                    switch (transferPair.get().getKey()) {
                        case "Current Customer":
                            transferTicket(vtag, vtn, currentCusTag,transferPair.get().getValue());
                            System.out.println(vticket+" Transfered to Current Customer Queue");
                            allListView.getItems().remove(allListView.getItems().size()-1);
                        break;
                        case "New Customer":
                            transferTicket(vtag, vtn, newCusTag,transferPair.get().getValue());
                            System.out.println(vticket+" Transfered to New Customer Queue");
                            allListView.getItems().remove(allListView.getItems().size()-1);
                        break;
                        case "Services":
                            transferTicket(vtag, vtn, serviceTag,transferPair.get().getValue());
                            System.out.println(vticket+" Transfered to Services Queue");
                            allListView.getItems().remove(allListView.getItems().size()-1);
                        break;
                        case "Others":
                            transferTicket(vtag, vtn, othersTag,transferPair.get().getValue());
                            System.out.println(vticket+" Transfered to Others Queue");
                            allListView.getItems().remove(allListView.getItems().size()-1);
                        break;
                    }
                }
            }
            else{createAlert(AlertType.WARNING, "Error", "This ticket was called already", null);}
            pool.releaseConnection(connect);
        }
        else{createAlert(AlertType.WARNING, "Empty Queue", "No ticket on queue", null);}
    }
    else{createAlert(AlertType.WARNING, "Error", "No ticket called", null);}
    }catch(Exception ex){}
    }
    public void callTransferedActionToPerform(ListView allListView, Label currentlyServing, String tag){
    try{
        Connection con=pool.getConnection();
        Statement stmt2 =con.createStatement();
        //first lock the ticket
        stmt2.executeUpdate("update transfer set locked = true, staff_no = '"+staffNoTextField.getText()+"' where time_called is null and locked is null and trans_to = '"+tag+"' limit 1");   
        //call ticket
        PreparedStatement call_ticket = con.prepareStatement("select tag, t_no from transfer where time_called is null and trans_to = '"+tag+"' and locked is not null and staff_no = '"+staffNoTextField.getText()+"' limit 1");
        String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
        ResultSet rs = call_ticket.executeQuery();
        if(rs.next()){
            if(!allListView.getItems().isEmpty()){
                if(allListView.getItems().size()>2)
                    allListView.getItems().remove(0);
                String v = allListView.getItems().get(allListView.getItems().size()-1).toString();
                String vtag = v.substring(0,1);
                String vtn = v.substring(1, v.indexOf(" "));
                PreparedStatement transferred = con.prepareStatement("select transferred from tickets where tag = '"+vtag+"' and t_no = '"+vtn+"'");
                ResultSet rs2 = transferred.executeQuery();
                if(rs2.next()){
                    String transfer = rs2.getString("transferred");
                    if(transfer!= null){
                        String updTimeDone = "update transfer set time_done= '"+currentTime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and staff_no = '"+staffNoTextField.getText()+"'";
                        stmt2.executeUpdate(updTimeDone);
                    }
                    else{
                        String updTimeDone = "update tickets set time_done= '"+currentTime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and staff_no = '"+staffNoTextField.getText()+"'";
                        stmt2.executeUpdate(updTimeDone);
                    }
                }
            }
            String vtag = rs.getString("tag");
            int vt_no = rs.getInt("t_no");
            String trnstkt = vtag + vt_no;
            PreparedStatement getService = con.prepareStatement("select service from transfer where tag = '"+vtag+"' and t_no = '"+vt_no+"'");
            ResultSet gs = getService.executeQuery();
            String service = "";
            while(gs.next()){
            service = gs.getString("service");}
            stmt2.executeUpdate("update transfer set time_called = '"+currentTime+"', staff_no = '"+staffNoTextField.getText()+"' where tag = '"+vtag+"' and t_no = '"+vt_no+"'");
            currentlyServing.setText(trnstkt);
            callTicketToDisplay(trnstkt);
            allListView.getItems().add(new ticket(vt_no, vtag) + " - " + service);
        }
        else{createAlert(AlertType.WARNING, "Empty Queue", "Transfered queue is empty", null);}
        pool.releaseConnection(con);
    }
        catch(SQLException e){e.printStackTrace();}
    }
    public void callAgainActionToPerform( ListView allListView, Label currentlyServing){
    try{
        Connection con=pool.getConnection();
        if(!allListView.getItems().isEmpty()){
            String gettkt = allListView.getItems().get(allListView.getItems().size()-1).toString();
            String tkt = gettkt.substring(0, gettkt.indexOf(" "));
            PreparedStatement call_ticket = con.prepareStatement("select time_done from tickets where tag = '"+gettkt.substring(0, 1)+"' and t_no = '"+gettkt.substring(1,gettkt.indexOf(" "))+"' limit 1");
            ResultSet rs = call_ticket.executeQuery();
            while(rs.next()){
                String t_done = rs.getString("time_done");
                if(t_done == null){
                    currentlyServing.setText(tkt);
                    flash(currentlyServing);
                    callTicketToDisplay(tkt);
                }
                else{
                    createAlert(AlertType.WARNING, "Error Calling Ticket", "Ticket cannot be called again", null);
                }
            }
        }
        else{
            createAlert(AlertType.WARNING, "Empty Queue", "Queue is empty", null);
        }
        pool.releaseConnection(con);
    }
    catch(SQLException e){e.printStackTrace();}
    }
    public void loginActionToPerform(){
    try{
        Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com -c 1");
        int x = process.waitFor();
        if (x != 0) {
            createAlert(AlertType.ERROR, "No internet connection", "No internet connection was detected, internet connection is required to login", "No internet connection detected");
        }
        else{
            Connection con=pool.getConnection();
            if(passwordTextField.getText().length() != 0 && staffNoTextField.getText().length() != 0){
                if(passwordTextField.getText().substring(passwordTextField.getText().length()-1).equals("\\")){shake(loginActionLabel);loginActionLabel.setText("Incorrect Staff No or Password");}
                else{
                    PreparedStatement get_pswd = con.prepareStatement("select password, count(*), online, staff_name, counter, staff_level from staff where staff_no = '"+staffNoTextField.getText()+"'");
                    PreparedStatement con_pswd = con.prepareStatement("select md5('"+passwordTextField.getText()+"') as password");
                    PreparedStatement get_tags = con.prepareStatement("select s_no, service from services");
                    ResultSet gp = get_pswd.executeQuery();
                    ResultSet cp = con_pswd.executeQuery();
                    ResultSet gt = get_tags.executeQuery();
                    while(gt.next()){
                        String services = gt.getString("service");
                        switch(services){
                            case "Current Customer":
                                currentCusTag = gt.getString("s_no");
                                break;
                            case "New Customer":
                                newCusTag = gt.getString("s_no");
                                break;
                            case "Services":
                                serviceTag = gt.getString("s_no");
                                break;
                            case "Others":
                                othersTag = gt.getString("s_no");
                                break;
                        }
                    }
                    while(gp.next() && cp.next()){
                        String gp1 = gp.getString("password");
                        String gp2 = gp.getString("count(*)");
                        String cp1 = cp.getString("password");
                        String gp3 = gp.getString("online");
                        String stfname = gp.getString("staff_name");
                        String serCounter = gp.getString("counter");
                        staff_level = gp.getString("staff_level");
                        //check to see if there is a staff with the staff no given
                        if(gp2.equals(String.valueOf(0))){shake(loginActionLabel);loginActionLabel.setText("Incorrect Staff No or Password");}
                        //check to see if that staff is already logged in
                        else if(gp3.equals("1")){shake(loginActionLabel);loginActionLabel.setText("Staff Logged In Already");}
                        else if(gp1.equals(cp1)){
                            String cbv =loginCombo.getSelectionModel().getSelectedItem();
                            if(cbv == null){shake(loginCombo);shake(loginActionLabel);loginActionLabel.setText("Please select a service");}
                            Statement stmt2 =con.createStatement();
                            //businessCusMenuItem.setEnabled(false);
                            newCusMenuItem.setDisable(true);
                            otherMenuItem.setDisable(true);
                            currentCusMenuItem.setDisable(true);
                            servicesMenuItem.setDisable(true);
                            MenuItem item = null;
                            Node node = null;
                            PreparedStatement get_services = con.prepareStatement("Select service from staff_level where level_id = '"+staff_level+"'");
                            ResultSet gs = get_services.executeQuery();
                            while(gs.next()){
                                String services = gs.getString("service");
                                switch(services){
                                    case "Current Customer":
                                        currentCusMenuItem.setDisable(false);
                                        item = currentCusMenuItem;
                                        node = currentCustomerNode;
                                    break;
                                    case "Services":
                                        servicesMenuItem.setDisable(false);
                                        item = servicesMenuItem;
                                        node = servicesNode;
                                    break;
                                    case "New Customer":
                                        newCusMenuItem.setDisable(false);
                                        item = newCusMenuItem;
                                        node = newCustomerNode;
                                    break;
                                    case "Others":
                                        otherMenuItem.setDisable(false);
                                        item = otherMenuItem;
                                        node = otherNode;
                                    break;
                                }
                                if(cbv != null){
                                    if(cbv.equals(services)){
                                        doFadeinDown(cardsStackPane, menuBar);
                                        doFadeInUpTransition(cardsStackPane, node);
                                        //menuBar.setVisible(true);
                                        //doSlideInFromTop(cardsStackPane, node);
                                        item.setDisable(true);
                                        stmt2.executeUpdate("update staff set online = '1' where staff_no = '"+staffNoTextField.getText()+"'");
                                        loggedInAsLabel.setText("["+staffNoTextField.getText()+"] "+stfname+"");
                                        counterLabel.setText("serving counter: "+serCounter+"");                                       
                                        showMissedTickets(currentCusMissedNoLabel, currentCusTag);
                                        showMissedTickets(newCusMissedNoLabel, newCusTag);
                                        showMissedTickets(servicesMissedNoLabel, serviceTag);
                                        showMissedTickets(otherMissedNoLabel, othersTag);
                                    }
                                    else{
                                        shake(loginCombo);shake(loginActionLabel);
                                        loginActionLabel.setText("Please Select An Appropriate Service");}
                                }
                            }                                                              
                        }
                        else{
                            shake(staffNoTextField);shake(passwordTextField);shake(loginActionLabel);
                            loginActionLabel.setText("Incorrect Staff No and/or Password");
                        }                             
                    }
                }
            }
            else{
                shake(staffNoTextField);shake(passwordTextField);shake(loginActionLabel);
                loginActionLabel.setText("Please fill in all the fields");
            }
            pool.releaseConnection(con);
        }
    }
    catch(Exception e){e.printStackTrace();}
}
    public void noShowActionToPerform(ListView allListView, Label missedNoLabel, String tag){
        if(!allListView.getItems().isEmpty()){
            String v = allListView.getItems().get(allListView.getItems().size()-1).toString();
            if(v != null){
                String vtag = v.substring(0,1);
                String vtn = v.substring(1,v.indexOf(" "));
                String time_done = "";
                String missing_client = "";
                String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                try{
                    Connection con = pool.getConnection();
                    PreparedStatement stmt = con.prepareStatement("select time_done, missing_client from tickets where tag = '"+vtag+"' and t_no = '"+vtn+"'");
                    ResultSet rs = stmt.executeQuery();
                    Statement stmt2 =con.createStatement();
                    if(rs.next()){
                        time_done = rs.getString("time_done");
                        missing_client = rs.getString("missing_client");
                        if(time_done  == null && missing_client == null){
                            stmt2.executeUpdate("update tickets set missing_client = '"+currentTime+"', time_called = Null where tag = '"+vtag+"' and t_no = '"+vtn+"' ");
                            allListView.getItems().remove(allListView.getItems().size()-1);
                            PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and tag = '"+tag+"' and staff_no = '"+staffNoTextField.getText()+"'");
                            ResultSet counter = count.executeQuery();
                            while(counter.next()){
                                String value = counter.getString("count(*)");
                                missedNoLabel.setText(value);
                            }
                        }
                        else{createAlert(AlertType.WARNING, "Error", "Ticket cannot be added to missed queue", null);}
                    }
                    pool.releaseConnection(con);
                }
                catch(SQLException e){e.printStackTrace();}
            }
            else{createAlert(AlertType.WARNING, "Empty queue", "Queue is empty", null);}
        }
        else{createAlert(AlertType.WARNING, "Error calling ticket", "No ticket called", null);}
    }
    public void callMissedActionToPerform(ListView allListView, Label currentlyServingTicketNumber,Label missedNoLabel){
    try{
        Connection con = pool.getConnection();
        PreparedStatement call_client = con.prepareStatement("select tag, t_no, service from tickets where missing_client is not null and time_called is null and staff_no = '"+staffNoTextField.getText()+"' limit 1");
        ResultSet cc = call_client.executeQuery();
        if(cc.next()){
            if(allListView.getItems().size()>2)
                allListView.getItems().remove(0);
            String vtag = cc.getString("tag");
            String vtn = cc.getString("t_no");
            String service = cc.getString("service");
            Statement stmt =con.createStatement();
            currentlyServingTicketNumber.setText(vtag+vtn);
            callTicketToDisplay(vtag+vtn);
            allListView.getItems().add(vtag + vtn + " - " + service);
            String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            stmt.executeUpdate("update tickets set time_called = '"+currentTime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"'");
            PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and staff_no = '"+staffNoTextField.getText()+"'");
            ResultSet counter = count.executeQuery();
            while(counter.next()){
                String value = counter.getString("count(*)");
                missedNoLabel.setText(value);
            }
            if(!allListView.getItems().isEmpty() && allListView.getItems().size()>1){
                String v = allListView.getItems().get(allListView.getItems().size()-2).toString();
                String vtag2 = v.substring(0,1);
                String vtn2 = v.substring(1,v.indexOf(" "));
                if(vtag2.equals(vtag)){
                    stmt.executeUpdate("update tickets set time_done= '"+currentTime+"' where tag = '"+vtag2+"' and t_no = '"+vtn2+"' and staff_no = '"+staffNoTextField.getText()+"' and time_done is null");
                }
                else{
                    stmt.executeUpdate("update transfer set time_done= '"+currentTime+"' where tag = '"+vtag2+"' and t_no = '"+vtn2+"' and staff_no = '"+staffNoTextField.getText()+"' and time_done is null");
                }
            }
        }
        else{createAlert(AlertType.WARNING, "Empty Queue", "Missed queue is empty", null);}
        pool.releaseConnection(con);
    }
    catch(SQLException e){System.out.println(e);}
}
    public void doneButtonPerformAction( ListView allListView, String tag){
        try{
            if(!allListView.getItems().isEmpty()){
               String v = allListView.getItems().get(allListView.getItems().size()-1).toString();
               if(v != null){
                   String localtime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                   Connection connect=pool.getConnection();
                   String vtag = v.substring(0,1);
                   String vtn = v.substring(1,v.indexOf(" "));
                   Statement stmt = connect.createStatement();
                   if(vtag.equals(tag)){
                           stmt.executeUpdate("update tickets set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null ");
                   }
                   else{
                           stmt.executeUpdate("update transfer set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null ");
                   }   
                   pool.releaseConnection(connect);
               }
               else{createAlert(AlertType.WARNING, "Empty Queue", "No ticket on queue", null);}
            }
            else{createAlert(AlertType.WARNING, "Empty Queue", "No ticket called", null);}
        }catch(SQLException ex){ex.printStackTrace();}
    }
    public void addTimeDone(ListView allListView, String tag){
        try{
            if(!allListView.getItems().isEmpty()){
               String v = allListView.getItems().get(allListView.getItems().size()-1).toString();
               if(v != null){
                   String localtime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                   Connection connect=pool.getConnection();
                   String vtag = v.substring(0,1);
                   String vtn = v.substring(1,v.indexOf(" "));
                   Statement stmt = connect.createStatement();
                   if(vtag.equals(tag)){
                       stmt.executeUpdate("update tickets set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null");
                   }
                   else{
                       System.out.println("It fucking did");
                       stmt.executeUpdate("update transfer set time_done = '"+localtime+"' where tag = '"+vtag+"' and t_no = '"+vtn+"' and time_done is null");
                   }   
                   pool.releaseConnection(connect);
               }
            }
        }catch(Exception ex){}
    }
    public void lockButtonPerformAction(ToggleButton lockTbtn, String service_no){
    try{
            Connection con = pool.getConnection();
            Statement stmt =con.createStatement();
            PreparedStatement ps = con.prepareStatement("select locked,staff_no from services where s_no = '"+service_no+"'");
            ResultSet rst =ps.executeQuery();
            while(rst.next()){
                String staff_id = rst.getString("staff_no");
                if(lockTbtn.isSelected()){
                    if(rst.getString("locked").equals("0")){
                        System.out.println("locking");
                        stmt.executeUpdate("update services set locked = '1', staff_no = '"+staffNoTextField.getText()+"' where s_no = '"+service_no+"'");
                    }
                    else{createAlert(AlertType.WARNING, "Queue Locked", "Queue was already locked by "+ staff_id +"!", "Queue is already locked"); }
                }
                else{stmt.executeUpdate("update services set locked = '0', staff_no = '"+staffNoTextField.getText()+"' where s_no = '"+service_no+"'");}
            }

        pool.releaseConnection(con);
    }
        catch(SQLException e){}
    }
    public void changeServicePerformAction(String itemToSelect, Node nodeToShow){
    try{
        loginCombo.getSelectionModel().select(itemToSelect);
        String selecteditem = loginCombo.getSelectionModel().getSelectedItem();
        doFadeInUpTransition(cardsStackPane, nodeToShow);
        Connection con= pool.getConnection();
        PreparedStatement get_services = con.prepareStatement("Select service from staff_level where level_id = '"+staff_level+"'");
        ResultSet gs = get_services.executeQuery();
        while(gs.next()){
            String services = gs.getString("service");
            switch(services){
                case "Current Customer":
                    if(!selecteditem.equals("Current Customer")) currentCusMenuItem.setDisable(false); else currentCusMenuItem.setDisable(true);
                break;
                case "Services":
                    //servicesMenuItem.setEnabled(true);
                    if(!selecteditem.equals("Services")) servicesMenuItem.setDisable(false); else servicesMenuItem.setDisable(true);
                break;
                case "New Customer":
                    //newCusMenuItem.setEnabled(true);
                    if(!selecteditem.equals("New Customer")) newCusMenuItem.setDisable(false); else newCusMenuItem.setDisable(true);
                break;
                case "Others":
                    //otherMenuItem.setEnabled(true);
                    if(!selecteditem.equals("Others")) otherMenuItem.setDisable(false); else otherMenuItem.setDisable(true);
                break;
                }
            }
        pool.releaseConnection(con);
        }   
        catch(SQLException ex){ex.printStackTrace();}
    }
    private static void showNode(StackPane sp, Node nodeToShow){
       sp.getChildren().clear();
       sp.getChildren().add(nodeToShow);
    }
    private static void switchNode(StackPane sp, Node nodeToSwitch, Node loginNode){
            try{
                FadeOutUpBigTransition fot = new FadeOutUpBigTransition(loginNode);
                fot.play();
                fot.wait();
                sp.getChildren().clear();
                loginNode.setOpacity(1);
                nodeToSwitch.setOpacity(1);
                sp.getChildren().add(nodeToSwitch);
            }
            catch(Exception e){}
        }
//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Transitions and Animations">
        protected static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);
        void shake(TextField tf){
            ShakeTransition st = new ShakeTransition(tf);
            st.play();
        }
        void shake(Label l){
            ShakeTransition st = new ShakeTransition(l);
            st.play();
        }
        void shake(ComboBox cb){
            ShakeTransition st = new ShakeTransition(cb);
            st.play();
        }
        void shake(PasswordField pf){
            ShakeTransition st = new ShakeTransition(pf);
            st.play();
        }
        void shake(Node n){
            ShakeTransition st = new ShakeTransition(n);
            st.play();
        }
        void flash(Label l){
            FlashTransition ft = new FlashTransition(l);
            ft.play();
        }
        void doSlideInFromTop(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
         Timeline tl = new Timeline(kf);
        tl.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        tl.play();
    }
        void doFadeInUpTransition(StackPane stackPane, Node paneToAdd){
            Node paneToRemove = stackPane.getChildren().get(0);
            paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
            stackPane.getChildren().add(paneToAdd);
            Timeline tl = new Timeline(
                    new KeyFrame(Duration.millis(0),    
                    new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                    new KeyValue(paneToAdd.translateYProperty(), 20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                    new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                    new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE)
                    ));
            tl.setOnFinished(evt -> stackPane.getChildren().remove(paneToRemove));
            tl.play();
        }
        void doFadeinDown(StackPane stackPane, MenuBar menuBar){
            FadeInDownTransition fid = new FadeInDownTransition(menuBar);
            menuBar.setVisible(true);
            fid.play();
        }
        void doFadeIn(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        stackPane.getChildren().add(paneToAdd);
         FadeTransition ft = new FadeTransition(Duration.millis(300));
        ft.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        ft.setNode(paneToAdd);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
        void doFadeInDownTransition(StackPane stackPane, Node paneToAdd){
            Node paneToRemove = stackPane.getChildren().get(0);
            paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
            stackPane.getChildren().add(paneToAdd);
            Timeline tl = new Timeline(
                   new KeyFrame(Duration.millis(0),    
                        new KeyValue(paneToAdd.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                        new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE)
                    ));
            tl.setOnFinished(evt -> stackPane.getChildren().remove(paneToRemove));
            tl.play();
        }
        void doSlideInFromLeft(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(-1 * stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
    }
//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Current Customer">
    @FXML void currentCusNextButtonActionPerformed(ActionEvent event) {
        nextButtonActionToPerform(currentCusAllListView, currentCusAutoTransferCB, currentCusCurrentlyServingLabel, currentCusTag);
    }
    @FXML void currentCusNoShowButtonActionPerformed(ActionEvent event){
        noShowActionToPerform(currentCusAllListView, currentCusMissedNoLabel, currentCusTag);
    }
    @FXML void currentCusTransferButtonActionPerformed(){
        transferButtonActionToPerform(currentCusAllListView);
    }
    @FXML void currentCusDoneButtonActionPerformed(){
        doneButtonPerformAction(currentCusAllListView, currentCusTag);
    }
    @FXML void currentCusCallAgainActionPerformed(){
        callAgainActionToPerform(currentCusAllListView, currentCusCurrentlyServingLabel);
    }
    @FXML void currentCusCallMissedActionPerformed(){
        callMissedActionToPerform(currentCusAllListView, currentCusCurrentlyServingLabel, currentCusMissedNoLabel);
    }
    @FXML void currentCusCallTransferedActionPerformed(){
        callTransferedActionToPerform(currentCusAllListView, currentCusCurrentlyServingLabel, currentCusTag);
    }
    @FXML void currentCusLockButtonActionPerformed(){
        lockButtonPerformAction(currentCusLockBtn, currentCusTag);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="New Customer">
    @FXML void newCusNextButtonActionPerformed(ActionEvent event) {
        nextButtonActionToPerform(newCusAllListView, newCusAutoTransferCB, newCusCurrentlyServingLabel, newCusTag);
    }
    @FXML void newCusNoShowButtonActionPerformed(ActionEvent event){
        noShowActionToPerform(newCusAllListView, newCusMissedNoLabel, newCusTag);
    }
    @FXML void newCusTransferButtonActionPerformed(){
        transferButtonActionToPerform(newCusAllListView);
    }
    @FXML void newCusDoneButtonActionPerformed(){
        doneButtonPerformAction(newCusAllListView, newCusTag);
    }
    @FXML void newCusCallAgainActionPerformed(){
        callAgainActionToPerform(newCusAllListView, newCusCurrentlyServingLabel);
    }
    @FXML void newCusCallMissedActionPerformed(){
        callMissedActionToPerform(newCusAllListView, newCusCurrentlyServingLabel, newCusMissedNoLabel);
    }
    @FXML void newCusCallTransferedActionPerformed(){
        callTransferedActionToPerform(newCusAllListView, newCusCurrentlyServingLabel, newCusTag);
    }
    @FXML void newCusLockButtonActionPerformed(){
        lockButtonPerformAction(newCusLockBtn, newCusTag);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Services">
    @FXML void servicesNextButtonActionPerformed(ActionEvent event) {
        nextButtonActionToPerform(servicesAllListView, servicesAutoTransferCB, servicesCurrentlyServingLabel, serviceTag);
    }
    @FXML void servicesNoShowButtonActionPerformed(ActionEvent event){
        noShowActionToPerform(servicesAllListView, servicesMissedNoLabel, serviceTag);
    }
    @FXML void servicesTransferButtonActionPerformed(){
        transferButtonActionToPerform(servicesAllListView);
    }
    @FXML void servicesDoneButtonActionPerformed(){
        doneButtonPerformAction(servicesAllListView, serviceTag);
    }
    @FXML void servicesCallAgainActionPerformed(){
        callAgainActionToPerform(servicesAllListView, servicesCurrentlyServingLabel);
    }
    @FXML void servicesCallMissedActionPerformed(){
        callMissedActionToPerform(servicesAllListView, servicesCurrentlyServingLabel, servicesMissedNoLabel);
    }
    @FXML void servicesCallTransferedActionPerformed(){
        callTransferedActionToPerform(servicesAllListView, servicesCurrentlyServingLabel, serviceTag);
    }
    @FXML void servicesLockButtonActionPerformed(){
        lockButtonPerformAction(servicesLockBtn, serviceTag);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other">
    @FXML void otherNextButtonActionPerformed(ActionEvent event) {
        nextButtonActionToPerform(otherAllListView, otherAutoTransferCB, otherCurrentlyServingLabel, othersTag);
    }
    @FXML void otherNoShowButtonActionPerformed(ActionEvent event){
        noShowActionToPerform(otherAllListView, otherMissedNoLabel,othersTag);
    }
    @FXML void otherTransferButtonActionPerformed(){
        transferButtonActionToPerform(otherAllListView);
    }
    @FXML void otherDoneButtonActionPerformed(){
        doneButtonPerformAction(otherAllListView, othersTag);
    }
    @FXML void otherCallAgainActionPerformed(){
        callAgainActionToPerform(otherAllListView, otherCurrentlyServingLabel);
    }
    @FXML void otherCallMissedActionPerformed(){
        callMissedActionToPerform(otherAllListView, otherCurrentlyServingLabel, otherMissedNoLabel);
    }
    @FXML void otherCallTransferedActionPerformed(){
        callTransferedActionToPerform(otherAllListView, otherCurrentlyServingLabel, othersTag);
    }
    @FXML void otherLockButtonActionPerformed(){
        lockButtonPerformAction(otherLockBtn, othersTag);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Change Service Menu">
    @FXML
    void currentCusMenuClicked(ActionEvent event) {
        changeServicePerformAction("Current Customer", currentCustomerNode);
    }
    @FXML
    public void newCusMenuClicked(ActionEvent event){
        changeServicePerformAction("New Customer", newCustomerNode);
    }
    @FXML
    void otherMenuClicked(ActionEvent event) {
        changeServicePerformAction("Others", otherNode);
    }
    @FXML
    void servicesMenuClicked(ActionEvent event) {
        changeServicePerformAction("Services", servicesNode);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Options Menu">
    @FXML public void logoutMenuActionPerformed(){
    try{
        switch (loginCombo.getSelectionModel().getSelectedItem()) {
            case "Current Customer":
                addTimeDone(currentCusAllListView,currentCusTag);
                break;
            case "Others":
                addTimeDone(otherAllListView, othersTag);
                break;
            case "New Customer":
                addTimeDone(newCusAllListView, newCusTag);
                break;
            case "Services":
                addTimeDone(servicesAllListView, serviceTag);
                break;
        }
        showNode(cardsStackPane, loginNode);
        passwordTextField.clear();
        Connection con=pool.getConnection();
        Statement stmt =con.createStatement();
        stmt.executeUpdate("update staff set online = '0' where staff_no = '"+staffNoTextField.getText()+"'");
        loginActionLabel.setText("");
        loggedInAsLabel.setText("");
        counterLabel.setText("");
        menuBar.setVisible(false);
        currentCusMenuItem.setDisable(false);
        newCusMenuItem.setDisable(false);
        servicesMenuItem.setDisable(false);
        otherMenuItem.setDisable(false);
        currentCusAllListView.getItems().clear();
        newCusAllListView.getItems().clear();
        servicesAllListView.getItems().clear();
        otherAllListView.getItems().clear();
        pool.releaseConnection(con);
    }
    catch(SQLException se){}
    }
    @FXML void changeCounterActionPerformed(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Change Counter");
        dialog.setHeaderText("Change Counter");
        dialog.setContentText("Type new counter:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            try{
                Connection con=pool.getConnection();
                PreparedStatement chk_counter = con.prepareStatement("select counter from staff");
                ResultSet chkc = chk_counter.executeQuery();
                boolean counterInUse = false ;
                while(chkc.next()){
                    String chkCounter = chkc.getString("counter");
                    if(result.get().equals(chkCounter)){
                        createAlert(AlertType.WARNING,"Error", "Counter in use", null);
                        changeCounterActionPerformed();
                        counterInUse = true;
                    }
                }
                if(chkc.isAfterLast() && counterInUse == false){
                    try{
                        if(Integer.valueOf(result.get()) > 0 && Integer.valueOf(result.get()) < 10){
                            Statement stmt =con.createStatement();
                            stmt.executeUpdate("update staff set counter ='"+result.get()+"' where staff_no = '"+staffNoTextField.getText()+"'");
                            counterLabel.setText("serving counter: "+result.get());
                            flash(counterLabel);
                            createAlert(AlertType.INFORMATION, "Counter Changed", "Counter was successfully changed", null);
                        }
                        else{createAlert(AlertType.ERROR, "Error", "Input Error!", null);changeCounterActionPerformed();}
                    }
                    catch(NumberFormatException e){;createAlert(AlertType.ERROR, "Error", "Input Error!", null);changeCounterActionPerformed();}    
                } 
                pool.releaseConnection(con);
            }
            catch(SQLException e){e.printStackTrace();}
        }
    //result.ifPresent(name -> System.out.println("Your name: " + name));
    }
    @FXML void changePasswordActionPerformed(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Change Password");
        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        ButtonType done = new ButtonType("Change", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField currentPassword = new PasswordField();
        currentPassword.setPromptText("Current Password");
        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("New password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm password");
        Label changePasswordActionLabel = new Label("");

        grid.add(new Label("Current Password:"), 0, 0);
        grid.add(currentPassword, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPassword, 1, 1);
        grid.add(new Label("Confirm Password:"), 0 ,2);
        grid.add(confirmPassword, 1, 2);
        grid.add(changePasswordActionLabel, 1, 3);

        // Enable/Disable login button depending on whether a username was entered.
        Node changeBtn = dialog.getDialogPane().lookupButton(done);
        changeBtn.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        currentPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            changeBtn.setDisable(newValue.trim().isEmpty());
        });
        
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> {
            currentPassword.requestFocus();
            //newPassword.requestFocus();
            //confirmPassword.requestFocus();
        });
        Connection con=pool.getConnection();
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == done) {
                try {
                    PreparedStatement get_pswd = con.prepareStatement("select password, online from staff where staff_no = '"+staffNoTextField.getText()+"'");
                    PreparedStatement con_pswd = con.prepareStatement("select md5('"+currentPassword.getText()+"') as password");
                    ResultSet cp = con_pswd.executeQuery();
                    ResultSet gp = get_pswd.executeQuery();
                    String gp1 = "";
                    String gonl = "";
                     String cp1 = "";
                    while(gp.next()){
                    gp1 = gp.getString("password");
                    gonl = gp.getString("online");}
                    while(cp.next()){
                       cp1 = cp.getString("password");}
                    if(gonl.equals("1")){
                        if(cp1.equals(gp1)){
                            if(newPassword.getText().equals(confirmPassword.getText())){
                                if(newPassword.getText().length()>4){
                                    return new Pair<>("Confirmed Password",confirmPassword.getText());
                                }
                                else{createAlert(AlertType.ERROR, "Error", "Password too short", null);changePasswordActionPerformed();}
                            }
                            else{createAlert(AlertType.ERROR, "Error", "Error changing password", "New passwords do not match");changePasswordActionPerformed();}
                        }
                        else{createAlert(AlertType.ERROR, "Error", "Error changing password", "Current password not correct!");changePasswordActionPerformed();}
                    }
                } catch (SQLException ex) {Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);}
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(passwordToChange -> {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("update staff set password = md5('"+passwordToChange.getValue()+"') where staff_no = '"+staffNoTextField.getText()+"'");
                createAlert(AlertType.INFORMATION, "Success", "Password successfully changed", null);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);}
        });
        pool.releaseConnection(con);
    }
    boolean plotPie = false;
    boolean plotLine=false;
    @FXML void statsButtonPressed(){
    try{
        doFadeIn(cardsStackPane, statsNode);
        menuBar.setVisible(false);
        Connection con = pool.getConnection();
            int timeNow = LocalTime.now().getHour();
            int num_of_tickets = 0;
            int start = 8;
            XYChart.Series dataSeries1 = new XYChart.Series();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if(plotLine = true){
                lineChart.getData().clear();
                lineChart.layout();
                plotLine = false;
            }

            int ia =0;
            while(start<=timeNow){
                String hour = String.valueOf(start);
                PreparedStatement timecount = con.prepareStatement("select (CASE WHEN time_done LIKE '"+hour+":%' THEN '"+hour+"' END) as hour, count(*) as num_of_tickets from tickets where staff_no = '"+staffNoTextField.getText()+"' group by (CASE WHEN time_done LIKE '"+hour+":%' THEN '"+hour+"' END)");
                ResultSet rsCount = timecount.executeQuery();
                while(rsCount.next()){
                    String time_done = rsCount.getString("hour");
                    if (time_done != null){num_of_tickets = rsCount.getInt("num_of_tickets");}
                    else{num_of_tickets = 0;}
                }
                dataSeries1.getData().add(ia,new XYChart.Data(hour, num_of_tickets));ia++;
                start+=1;
            }
            System.out.println(dataSeries1.getData());
            if(plotLine == false){
                lineChart.getData().add(dataSeries1);
            }
            PreparedStatement service_count = con.prepareStatement("select tag, count(tag) as amount from tickets where staff_no = '"+staffNoTextField.getText()+"' group by tag;");
            ResultSet rsSCount = service_count.executeQuery();
            
            if(plotPie == true){
                    //PreparedStatement get_tag_count = con.prepareStatement("select max(t_no) from tickets where tag = 'A' and staff_no = 'admin'")
                    //statsPieChart.getData().add(new PieChart.Data(service, amount));
                    pieChartData.clear();
                    statsPieChart.getData().clear();
                    plotPie=false;
                }
            while(rsSCount.next()){
                String tag = rsSCount.getString("tag");
                String service = "";
                float amount = rsSCount.getFloat("amount");
                if(plotPie == false){
                   if(tag.equals(currentCusTag)) service = "Current Customer";
                    else if(tag.equals(newCusTag)) service = "New Customer";
                    else if(tag.equals(serviceTag)) service = "Services";
                    else if(tag.equals(othersTag)) service = "Others";
                    pieChartData.add(new PieChart.Data(service, amount));  
                }   
            }
            if(plotPie == false){
                statsPieChart.getData().addAll(pieChartData);
                 final Label caption = new Label("");
                caption.setTextFill(Color.WHITE);
                caption.setStyle("-fx-font: 24 arial;");

                statsPieChart.getData().forEach((data) -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                    }); });
                plotPie = true;
            }
           
            
                //average wait time and average service time
                // tickets table
                PreparedStatement test = con.prepareStatement("select time_created, time_called from tickets where staff_no = '"+staffNoTextField.getText()+"' and time_called is not null");
                PreparedStatement test2 = con.prepareStatement("select time_called, time_done from tickets where staff_no = '"+staffNoTextField.getText()+"' and time_done is not null ");                
                // transfer table
                PreparedStatement trans_table = con.prepareStatement("select time_trans, time_called from transfer where staff_no = '"+staffNoTextField.getText()+"' and time_called is not null");
                PreparedStatement trans_table2 = con.prepareStatement("select time_called, time_done from transfer where staff_no = '"+staffNoTextField.getText()+"' and time_done is not null ");
                
                ResultSet rs = test.executeQuery();
                ResultSet rs2 = test2.executeQuery();
                ResultSet rs3 = trans_table.executeQuery();
                ResultSet rs4 = trans_table2.executeQuery();
                
                int count = 0;
                int count2 = 0;
                int wait_time = 0;
                int service_time = 0;
                //tickets wait time
                while(rs.next()){
                    String time_created = rs.getString("time_created");
                    String time_called = rs.getString("time_called");                   
                    int created= LocalTime.parse(time_created).toSecondOfDay();
                    int called = LocalTime.parse(time_called).toSecondOfDay();
                    int minusTime = called - created;
                    wait_time += minusTime;                   
                    count += 1;
                }
                //transfer wait time
                while(rs3.next()){
                    String time_trans = rs3.getString("time_trans");
                    String time_called = rs3.getString("time_called");                   
                    int trans= LocalTime.parse(time_trans).toSecondOfDay();
                    int called = LocalTime.parse(time_called).toSecondOfDay();
                    int minusTime = called - trans;
                    wait_time += minusTime;                   
                    count += 1;
                }
                // tickets service time
                while(rs2.next()){
                    String time_done = rs2.getString("time_done");
                    String time_called = rs2.getString("time_called");
                    int done= LocalTime.parse(time_done).toSecondOfDay();
                    int called = LocalTime.parse(time_called).toSecondOfDay();
                    int minusTime = done - called;
                    service_time += minusTime;                   
                    count2 += 1;
                }
                // transfer service time
                while(rs4.next()){
                    String time_done = rs4.getString("time_done");
                    String time_called = rs4.getString("time_called");
                    int done= LocalTime.parse(time_done).toSecondOfDay();
                    int called = LocalTime.parse(time_called).toSecondOfDay();
                    int minusTime = done - called;
                    service_time += minusTime;                   
                    count2 += 1;
                }
                int avgWaitTime = 0;
                if (count>0){avgWaitTime = wait_time/count;}
                long avgServiceTime = 0;
                if (count2>0){
                avgServiceTime = service_time/count2;}
                LocalTime result = LocalTime.ofSecondOfDay(avgWaitTime);
                LocalTime result2 = LocalTime.ofSecondOfDay(avgServiceTime);
                avgWaitTimeLabel.setText(""+result);
                if(avgServiceTime <= 300){
                    productivityLabel.setText("100%");
                }
                else{
                    float ideal_average = 300;
                    int percent = 100;
                    float t = ideal_average/avgServiceTime;
                    System.out.println(t);
                    int time = (int) (t * percent);
                    productivityLabel.setText(""+time+"%");
                }
                peopleAttentedTO.setText(""+count2);
                avgServiceTimeLabel.setText(""+result2);
            pool.releaseConnection(con);
        }catch(Exception e){e.printStackTrace();}
    }
    @FXML void statsBackButtonPressed(){
        menuBar.setVisible(true);
        String selectedItem = loginCombo.getSelectionModel().getSelectedItem();
            switch(selectedItem){
                case "Current Customer":
                    doSlideInFromLeft(cardsStackPane, currentCustomerNode);
                break;
                case "Services":
                    doSlideInFromLeft(cardsStackPane, servicesNode);
                break;
                case "New Customer":
                    doSlideInFromLeft(cardsStackPane, newCustomerNode);
                break;
                case "Others":
                    doSlideInFromLeft(cardsStackPane, otherNode);
                break;
                }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Help Menu">
    @FXML void checkForUpdatesPressed(){
        
    }
    @FXML void aboutPressed(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About");
        alert.setContentText("Queue Deck LLC");

        alert.showAndWait();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Login">
    @FXML public void loginButtonPressed(ActionEvent event) {
        loginActionToPerform();
    }
    @FXML void loignNodeKeyPressed(KeyEvent event){
    if(event.getCode() == KeyCode.ENTER){
        loginActionToPerform();
    }
    }
    @FXML void loginComboMouseClicked(MouseEvent event) {
        loginActionLabel.setText("");
    }
//</editor-fold>
    
    @Override
    public void initialize(URL Url, ResourceBundle rb) {
        loginCombo.getItems().clear();
        loginCombo.getItems().addAll("Current Customer","Services","New Customer","Others");
        menuBar.setVisible(false);
        showNode(cardsStackPane, loginNode);
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        staffNoTextField.setText(prefs.get("Staff No", ""));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Connection con2 = pool.getConnection();
                while(true){
                    Platform.runLater(() -> {
                        try {
                            if(loginCombo.getSelectionModel().getSelectedItem() != null){
                            String cbv =loginCombo.getSelectionModel().getSelectedItem();
                            switch(cbv){
                            case "Others":
                                try{
                                //Connection con2 = connectionPool.getConnection();
                                PreparedStatement countInLine = con2.prepareStatement("select sum(case when time_called IS NULL and tag = 'D' then 1 else 0 end) as count_num from tickets");
                                PreparedStatement countInLineb = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'D' then 1 else 0 end) as count_num from transfer");
                                ResultSet rs5 = countInLine.executeQuery();
                                ResultSet rs5b = countInLineb.executeQuery();
                                while(rs5.next() && rs5b.next()){
                                    String ppline = rs5.getString("count_num");
                                    String pplineb = rs5b.getString("count_num");
                                    if(ppline == null){
                                        ppline = String.valueOf(0);otherNoInline.setText(ppline);}
                                    else{
                                         if(pplineb != null){ppline =String.valueOf(Integer.valueOf(ppline) + Integer.valueOf(pplineb));}
                                        otherNoInline.setText(ppline);}
                                    }
                                PreparedStatement countTrans4 = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'D' then 1 else 0 end) as count_num_trans from transfer");
                                ResultSet rst2 =countTrans4.executeQuery();
                                while(rst2.next()){
                                    String noTrans = rst2.getString("count_num_trans");
                                    otherTranferNoLabel.setText(noTrans);
                                    }
                                }catch(NumberFormatException | SQLException e){System.err.print(e);}
                                break;
                            case "Current Customer":
                                try{
                                    //Connection con2 = connectionPool.getConnection();
                                    PreparedStatement countInLine = con2.prepareStatement("select sum(case when time_called IS NULL and tag = 'A' then 1 else 0 end) as count_num from tickets");
                                    PreparedStatement countInLineb = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'A' then 1 else 0 end) as count_num from transfer");
                                    ResultSet rs5 = countInLine.executeQuery();
                                    ResultSet rs5b = countInLineb.executeQuery();
                                    while(rs5.next() && rs5b.next()){
                                        String ppline = rs5.getString("count_num");
                                        String pplineb = rs5b.getString("count_num");
                                        if(ppline == null){
                                            ppline = String.valueOf(0);currentCusNoInline.setText(ppline);}
                                        else{
                                             if(pplineb != null){ppline =String.valueOf(Integer.valueOf(ppline) + Integer.valueOf(pplineb));}
                                            currentCusNoInline.setText(ppline);}
                                        }
                                    PreparedStatement countTrans4 = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'A' then 1 else 0 end) as count_num_trans from transfer");
                                    ResultSet rst2 =countTrans4.executeQuery();
                                    while(rst2.next()){
                                        String noTrans = rst2.getString("count_num_trans");
                                        currentCustomerTranferNoLabel.setText(noTrans);
                                        }
                                    }catch(NumberFormatException | SQLException e){e.printStackTrace();}
                                    break;
                            case "New Customer":
                                try{
                                    //Connection con2 = connectionPool.getConnection();
                                    PreparedStatement countInLine = con2.prepareStatement("select sum(case when time_called IS NULL and tag = 'B' then 1 else 0 end) as count_num from tickets");
                                    PreparedStatement countInLineb = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'B' then 1 else 0 end) as count_num from transfer");
                                    ResultSet rs5 = countInLine.executeQuery();
                                    ResultSet rs5b = countInLineb.executeQuery();
                                    while(rs5.next() && rs5b.next()){
                                        String ppline = rs5.getString("count_num");
                                        String pplineb = rs5b.getString("count_num");
                                        if(ppline == null){
                                            ppline = String.valueOf(0);newCusNoInline.setText(ppline);}
                                        else{
                                             if(pplineb != null){ppline =String.valueOf(Integer.valueOf(ppline) + Integer.valueOf(pplineb));}
                                            newCusNoInline.setText(ppline);}
                                        }
                                    PreparedStatement countTrans4 = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'B' then 1 else 0 end) as count_num_trans from transfer");
                                    ResultSet rst2 =countTrans4.executeQuery();
                                    while(rst2.next()){
                                        String noTrans = rst2.getString("count_num_trans");
                                        newCustomerTranferNoLabel.setText(noTrans);
                                        }
                                    }catch(NumberFormatException | SQLException e){e.printStackTrace();}
                                break;
                            case "Services":
                                try{
                                    //Connection con2 = connectionPool.getConnection();
                                    PreparedStatement countInLine = con2.prepareStatement("select sum(case when time_called IS NULL and tag = 'C' then 1 else 0 end) as count_num from tickets");
                                    PreparedStatement countInLineb = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'C' then 1 else 0 end) as count_num from transfer");
                                    ResultSet rs5 = countInLine.executeQuery();
                                    ResultSet rs5b = countInLineb.executeQuery();
                                    while(rs5.next() && rs5b.next()){
                                        String ppline = rs5.getString("count_num");
                                        String pplineb = rs5b.getString("count_num");
                                        if(ppline == null){
                                            ppline = String.valueOf(0);servicesNoInline.setText(ppline);}
                                        else{
                                             if(pplineb != null){ppline =String.valueOf(Integer.valueOf(ppline) + Integer.valueOf(pplineb));}
                                            servicesNoInline.setText(ppline);}
                                        }
                                    PreparedStatement countTrans4 = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = 'C' then 1 else 0 end) as count_num_trans from transfer");
                                    ResultSet rst2 =countTrans4.executeQuery();
                                    while(rst2.next()){
                                        String noTrans = rst2.getString("count_num_trans");
                                        servicesTranferNoLabel.setText(noTrans);
                                        }
                                    }catch(NumberFormatException | SQLException e){e.printStackTrace();}
                                break;
                            case "Business Customers":

                                break;
                                }
                            }
                        }
                         catch (Exception ex) {ex.printStackTrace();}
                    });
                    Thread.sleep(1000);   
                }
                //pool.releaseConnection(con2);
            }
        };
        new Thread(task).start();
        
    }
}
