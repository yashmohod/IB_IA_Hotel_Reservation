/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;
import java.sql.*;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Frank
 */
public class admin_options extends javax.swing.JFrame {
    //diable all the errors
    public void error_disable()
     {
         error1_lable.setVisible(false);
         error2_lable.setVisible(false);
         error3_lable.setVisible(false);
         error4_lable.setVisible(false);
         error5_lable.setVisible(false);
         error6_lable.setVisible(false);
         error7_lable.setVisible(false);
         firstName_textbox.setText("");
         lastName_textbox.setText("");
         username_textbox.setText("");
         password_textbox.setText("");
         employeeNo_textbox.setText("");
         passwordRe_textbox.setText("");
         
         searchAll.doClick();
         
     }
    //connection variables
    public Connection cn;
    public Statement st;
    //connection method
    /*public void conection() {

        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loop_hotel?zeroDateTimeBehavior=convertToNull", "root", "");
            st = cn.createStatement();
            System.out.println("conected");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "not conected");
        }
    }*/
    
    public admin_options() {
        initComponents();
        this.setLocationRelativeTo(null);
        service_pane.setVisible(true);
        accountMgmt_pane.setVisible(false);
        accountManagement_pane.setVisible(false);
        error_disable();
        searchAll.doClick();
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        dob_datechooser.setMaxSelectableDate(today);
         
    }
    public String username;
    public admin_options(String username_, Connection con) {
        initComponents();
        this.setLocationRelativeTo(null);
        service_pane.setVisible(true);
        accountMgmt_pane.setVisible(false);
        accountManagement_pane.setVisible(false);
        error_disable();
        cn = con;
        username = username_;
        searchAll.doClick();
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        dob_datechooser.setMaxSelectableDate(today);
    }
public String SearchBy = "";
private boolean size = true;
public int x ;
public int y;
static int xx,yy;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchBy_buttonGroup = new javax.swing.ButtonGroup();
        master_pane = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        style1_pane = new javax.swing.JPanel();
        hotel_lable = new javax.swing.JLabel();
        reservation_lable = new javax.swing.JLabel();
        servicing_button = new javax.swing.JButton();
        employeeSignUp_button = new javax.swing.JButton();
        accountMgmt_button = new javax.swing.JButton();
        employeeSignUp_button1 = new javax.swing.JButton();
        masterLayered_pane = new javax.swing.JLayeredPane();
        service_pane = new javax.swing.JPanel();
        removeRoomOutOfService_lable = new javax.swing.JLabel();
        addRoom_lable = new javax.swing.JLabel();
        addRemoveRoom_button = new javax.swing.JButton();
        putRoomInOutOfService_button = new javax.swing.JButton();
        addRoom_lable1 = new javax.swing.JLabel();
        addRemoveRoomType_button = new javax.swing.JButton();
        accountMgmt_pane = new javax.swing.JPanel();
        firstName_lable1 = new javax.swing.JLabel();
        lastName_lable1 = new javax.swing.JLabel();
        authorityLevel_label = new javax.swing.JLabel();
        authorityLevel_combobox = new javax.swing.JComboBox<>();
        lastName_textbox = new javax.swing.JTextField();
        accountMake_button = new javax.swing.JButton();
        dob_label = new javax.swing.JLabel();
        gender_label = new javax.swing.JLabel();
        firstName_textbox = new javax.swing.JTextField();
        gender_combobox = new javax.swing.JComboBox<>();
        employeeNo_label = new javax.swing.JLabel();
        employeeNo_textbox = new javax.swing.JTextField();
        password_lable = new javax.swing.JLabel();
        username_lable = new javax.swing.JLabel();
        password_textbox = new javax.swing.JTextField();
        username_textbox = new javax.swing.JTextField();
        error1_lable = new javax.swing.JLabel();
        error2_lable = new javax.swing.JLabel();
        error3_lable = new javax.swing.JLabel();
        error4_lable = new javax.swing.JLabel();
        error5_lable = new javax.swing.JLabel();
        error7_lable = new javax.swing.JLabel();
        error6_lable = new javax.swing.JLabel();
        passwordRe_lable = new javax.swing.JLabel();
        passwordRe_textbox = new javax.swing.JTextField();
        dob_datechooser = new com.toedter.calendar.JDateChooser();
        accountManagement_pane = new javax.swing.JPanel();
        serachEmployee_lable = new javax.swing.JLabel();
        serachBox_textfield = new javax.swing.JTextField();
        searchByName_radio = new javax.swing.JRadioButton();
        searchByEmpNo_radio = new javax.swing.JRadioButton();
        search_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeTable_table = new javax.swing.JTable();
        deleteEmployee_button = new javax.swing.JButton();
        selectedEmployee_textfield = new javax.swing.JTextField();
        serachEmployee_lable1 = new javax.swing.JLabel();
        editEmployee_button = new javax.swing.JButton();
        searchAll = new javax.swing.JRadioButton();
        closeButton1 = new javax.swing.JButton();
        closeButton2 = new javax.swing.JButton();
        closeButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        master_pane.setBackground(new java.awt.Color(255, 255, 255));

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        style1_pane.setBackground(new java.awt.Color(51, 102, 255));

        hotel_lable.setBackground(new java.awt.Color(0, 204, 255));
        hotel_lable.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 36)); // NOI18N
        hotel_lable.setForeground(new java.awt.Color(255, 255, 255));
        hotel_lable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hotel_lable.setText("Reservation");
        hotel_lable.setToolTipText("");

        reservation_lable.setBackground(new java.awt.Color(0, 204, 255));
        reservation_lable.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 36)); // NOI18N
        reservation_lable.setForeground(new java.awt.Color(255, 255, 255));
        reservation_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        reservation_lable.setText("Hotel ");
        reservation_lable.setToolTipText("");

        servicing_button.setBackground(new java.awt.Color(255, 255, 255));
        servicing_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        servicing_button.setForeground(new java.awt.Color(51, 102, 255));
        servicing_button.setText("Servicing");
        servicing_button.setToolTipText("");
        servicing_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicing_buttonActionPerformed(evt);
            }
        });

        employeeSignUp_button.setBackground(new java.awt.Color(255, 255, 255));
        employeeSignUp_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        employeeSignUp_button.setForeground(new java.awt.Color(51, 102, 255));
        employeeSignUp_button.setText("Account MGMT");
        employeeSignUp_button.setToolTipText("");
        employeeSignUp_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeSignUp_buttonActionPerformed(evt);
            }
        });

        accountMgmt_button.setBackground(new java.awt.Color(255, 255, 255));
        accountMgmt_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        accountMgmt_button.setForeground(new java.awt.Color(51, 102, 255));
        accountMgmt_button.setText("Account Sign Up");
        accountMgmt_button.setToolTipText("");
        accountMgmt_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountMgmt_buttonActionPerformed(evt);
            }
        });

        employeeSignUp_button1.setBackground(new java.awt.Color(255, 255, 255));
        employeeSignUp_button1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        employeeSignUp_button1.setForeground(new java.awt.Color(51, 102, 255));
        employeeSignUp_button1.setText("Print / Save");
        employeeSignUp_button1.setToolTipText("");
        employeeSignUp_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeSignUp_button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout style1_paneLayout = new javax.swing.GroupLayout(style1_pane);
        style1_pane.setLayout(style1_paneLayout);
        style1_paneLayout.setHorizontalGroup(
            style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addGroup(style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hotel_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, style1_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reservation_lable)
                        .addGap(50, 50, 50)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountMgmt_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servicing_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeeSignUp_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeeSignUp_button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        style1_paneLayout.setVerticalGroup(
            style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(reservation_lable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hotel_lable)
                .addGap(62, 62, 62)
                .addComponent(servicing_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(accountMgmt_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeeSignUp_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeeSignUp_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        masterLayered_pane.setPreferredSize(new java.awt.Dimension(800, 0));

        service_pane.setBackground(new java.awt.Color(255, 255, 255));
        service_pane.setForeground(new java.awt.Color(255, 255, 255));

        removeRoomOutOfService_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        removeRoomOutOfService_lable.setForeground(new java.awt.Color(51, 102, 255));
        removeRoomOutOfService_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        removeRoomOutOfService_lable.setText("Put room In / Out of Service  :-");

        addRoom_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addRoom_lable.setForeground(new java.awt.Color(51, 102, 255));
        addRoom_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        addRoom_lable.setText("Add / Remove Room  :-");

        addRemoveRoom_button.setBackground(new java.awt.Color(51, 102, 255));
        addRemoveRoom_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addRemoveRoom_button.setForeground(new java.awt.Color(255, 255, 255));
        addRemoveRoom_button.setText("Add/Remove");
        addRemoveRoom_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRemoveRoom_buttonActionPerformed(evt);
            }
        });

        putRoomInOutOfService_button.setBackground(new java.awt.Color(51, 102, 255));
        putRoomInOutOfService_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        putRoomInOutOfService_button.setForeground(new java.awt.Color(255, 255, 255));
        putRoomInOutOfService_button.setText("Add/Remove");
        putRoomInOutOfService_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                putRoomInOutOfService_buttonActionPerformed(evt);
            }
        });

        addRoom_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addRoom_lable1.setForeground(new java.awt.Color(51, 102, 255));
        addRoom_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        addRoom_lable1.setText("Add / Remove Room Type  :-");

        addRemoveRoomType_button.setBackground(new java.awt.Color(51, 102, 255));
        addRemoveRoomType_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addRemoveRoomType_button.setForeground(new java.awt.Color(255, 255, 255));
        addRemoveRoomType_button.setText("Add/Remove");
        addRemoveRoomType_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRemoveRoomType_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout service_paneLayout = new javax.swing.GroupLayout(service_pane);
        service_pane.setLayout(service_paneLayout);
        service_paneLayout.setHorizontalGroup(
            service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(service_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addRoom_lable1)
                    .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(removeRoomOutOfService_lable)
                        .addComponent(addRoom_lable, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(18, 18, 18)
                .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addRemoveRoom_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(putRoomInOutOfService_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addRemoveRoomType_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );
        service_paneLayout.setVerticalGroup(
            service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(service_paneLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeRoomOutOfService_lable)
                    .addComponent(putRoomInOutOfService_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRemoveRoom_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addRoom_lable))
                .addGap(20, 20, 20)
                .addGroup(service_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRoom_lable1)
                    .addComponent(addRemoveRoomType_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(393, Short.MAX_VALUE))
        );

        accountMgmt_pane.setBackground(new java.awt.Color(255, 255, 255));
        accountMgmt_pane.setPreferredSize(new java.awt.Dimension(796, 621));

        firstName_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable1.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable1.setText(" First Name  :-");

        lastName_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastName_lable1.setForeground(new java.awt.Color(51, 102, 255));
        lastName_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lastName_lable1.setText("Last Name  :-");

        authorityLevel_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        authorityLevel_label.setForeground(new java.awt.Color(51, 102, 255));
        authorityLevel_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        authorityLevel_label.setText("Authority Level :-");

        authorityLevel_combobox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        authorityLevel_combobox.setForeground(new java.awt.Color(51, 102, 255));
        authorityLevel_combobox.setMaximumRowCount(300);
        authorityLevel_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Common_Access", "Admin_Access" }));

        lastName_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastName_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastName_textboxMouseClicked(evt);
            }
        });
        lastName_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastName_textboxActionPerformed(evt);
            }
        });

        accountMake_button.setBackground(new java.awt.Color(51, 102, 255));
        accountMake_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        accountMake_button.setForeground(new java.awt.Color(255, 255, 255));
        accountMake_button.setText("Sign_up");
        accountMake_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountMake_buttonActionPerformed(evt);
            }
        });

        dob_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dob_label.setForeground(new java.awt.Color(51, 102, 255));
        dob_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dob_label.setText("DOB :-");
        dob_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dob_labelMouseClicked(evt);
            }
        });

        gender_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        gender_label.setForeground(new java.awt.Color(51, 102, 255));
        gender_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gender_label.setText("Gender:-");
        gender_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gender_labelMouseClicked(evt);
            }
        });

        firstName_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstName_textboxMouseClicked(evt);
            }
        });
        firstName_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstName_textboxActionPerformed(evt);
            }
        });

        gender_combobox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gender_combobox.setForeground(new java.awt.Color(51, 102, 255));
        gender_combobox.setMaximumRowCount(300);
        gender_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));

        employeeNo_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        employeeNo_label.setForeground(new java.awt.Color(51, 102, 255));
        employeeNo_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        employeeNo_label.setText("Employee No. :-");
        employeeNo_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeNo_labelMouseClicked(evt);
            }
        });

        employeeNo_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        employeeNo_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeNo_textboxMouseClicked(evt);
            }
        });

        password_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        password_lable.setForeground(new java.awt.Color(51, 102, 255));
        password_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        password_lable.setText(" Password  :-");

        username_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        username_lable.setForeground(new java.awt.Color(51, 102, 255));
        username_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        username_lable.setText(" Username  :-");

        password_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        password_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                password_textboxMouseClicked(evt);
            }
        });
        password_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_textboxActionPerformed(evt);
            }
        });

        username_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        username_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                username_textboxMouseClicked(evt);
            }
        });
        username_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_textboxActionPerformed(evt);
            }
        });

        error1_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error1_lable.setForeground(new java.awt.Color(204, 0, 0));
        error1_lable.setText("*");

        error2_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error2_lable.setForeground(new java.awt.Color(204, 0, 0));
        error2_lable.setText("*");

        error3_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error3_lable.setForeground(new java.awt.Color(204, 0, 0));
        error3_lable.setText("*");

        error4_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error4_lable.setForeground(new java.awt.Color(204, 0, 0));
        error4_lable.setText("*");

        error5_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error5_lable.setForeground(new java.awt.Color(204, 0, 0));
        error5_lable.setText("*");

        error7_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error7_lable.setForeground(new java.awt.Color(204, 0, 0));
        error7_lable.setText("*");

        error6_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error6_lable.setForeground(new java.awt.Color(204, 0, 0));
        error6_lable.setText("*");

        passwordRe_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        passwordRe_lable.setForeground(new java.awt.Color(51, 102, 255));
        passwordRe_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        passwordRe_lable.setText(" Re-enter Password  :-");

        passwordRe_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        passwordRe_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordRe_textboxMouseClicked(evt);
            }
        });
        passwordRe_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordRe_textboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout accountMgmt_paneLayout = new javax.swing.GroupLayout(accountMgmt_pane);
        accountMgmt_pane.setLayout(accountMgmt_paneLayout);
        accountMgmt_paneLayout.setHorizontalGroup(
            accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountMgmt_paneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(accountMake_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                .addGap(357, 357, 357)
                                .addComponent(employeeNo_label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(employeeNo_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(error7_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(username_lable)
                                    .addComponent(password_lable, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                        .addComponent(password_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(error5_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                        .addComponent(username_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(error4_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(authorityLevel_label)
                                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(gender_label)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gender_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(authorityLevel_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountMgmt_paneLayout.createSequentialGroup()
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountMgmt_paneLayout.createSequentialGroup()
                                .addComponent(dob_label)
                                .addGap(16, 16, 16))
                            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lastName_lable1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(firstName_lable1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastName_textbox)
                            .addComponent(firstName_textbox)
                            .addComponent(dob_datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(error1_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(error2_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(error3_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(145, 145, 145))
                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                        .addComponent(passwordRe_lable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordRe_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(error6_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        accountMgmt_paneLayout.setVerticalGroup(
            accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                        .addComponent(error2_lable)
                        .addGap(32, 32, 32)
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dob_label)
                            .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(error3_lable)))
                        .addGap(31, 31, 31))
                    .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstName_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(error1_lable)
                            .addComponent(firstName_lable1))
                        .addGap(9, 9, 9)
                        .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastName_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastName_lable1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dob_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username_lable)
                    .addComponent(error4_lable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password_lable)
                    .addComponent(error5_lable))
                .addGap(18, 18, 18)
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordRe_lable)
                    .addComponent(passwordRe_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(error6_lable))
                .addGap(46, 46, 46)
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(authorityLevel_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorityLevel_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeNo_label)
                    .addComponent(employeeNo_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(error7_lable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accountMgmt_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(accountMgmt_paneLayout.createSequentialGroup()
                            .addComponent(accountMake_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountMgmt_paneLayout.createSequentialGroup()
                            .addComponent(gender_label)
                            .addGap(45, 45, 45)))
                    .addComponent(gender_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        accountManagement_pane.setBackground(new java.awt.Color(255, 255, 255));
        accountManagement_pane.setPreferredSize(new java.awt.Dimension(796, 621));

        serachEmployee_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        serachEmployee_lable.setForeground(new java.awt.Color(51, 102, 255));
        serachEmployee_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        serachEmployee_lable.setText("Search Employee");

        serachBox_textfield.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        serachBox_textfield.setForeground(new java.awt.Color(51, 102, 255));

        searchByName_radio.setBackground(new java.awt.Color(255, 255, 255));
        searchBy_buttonGroup.add(searchByName_radio);
        searchByName_radio.setForeground(new java.awt.Color(51, 102, 255));
        searchByName_radio.setText("Search By Last Name");
        searchByName_radio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByName_radioActionPerformed(evt);
            }
        });

        searchByEmpNo_radio.setBackground(new java.awt.Color(255, 255, 255));
        searchBy_buttonGroup.add(searchByEmpNo_radio);
        searchByEmpNo_radio.setForeground(new java.awt.Color(51, 102, 255));
        searchByEmpNo_radio.setSelected(true);
        searchByEmpNo_radio.setText("Search By Employee No.");
        searchByEmpNo_radio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByEmpNo_radioActionPerformed(evt);
            }
        });

        search_button.setBackground(new java.awt.Color(51, 102, 255));
        search_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        search_button.setForeground(new java.awt.Color(255, 255, 255));
        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        employeeTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee No.", "First Name", "Last Name", "Gender", "Date of Birth", "Authority"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        employeeTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTable_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(employeeTable_table);

        deleteEmployee_button.setBackground(new java.awt.Color(51, 102, 255));
        deleteEmployee_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deleteEmployee_button.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmployee_button.setText("Delete");
        deleteEmployee_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmployee_buttonActionPerformed(evt);
            }
        });

        selectedEmployee_textfield.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        selectedEmployee_textfield.setForeground(new java.awt.Color(51, 102, 255));
        selectedEmployee_textfield.setEnabled(false);
        selectedEmployee_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedEmployee_textfieldActionPerformed(evt);
            }
        });

        serachEmployee_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        serachEmployee_lable1.setForeground(new java.awt.Color(51, 102, 255));
        serachEmployee_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        serachEmployee_lable1.setText("Selected Employee");

        editEmployee_button.setBackground(new java.awt.Color(51, 102, 255));
        editEmployee_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        editEmployee_button.setForeground(new java.awt.Color(255, 255, 255));
        editEmployee_button.setText("Edit");
        editEmployee_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEmployee_buttonActionPerformed(evt);
            }
        });

        searchAll.setBackground(new java.awt.Color(255, 255, 255));
        searchBy_buttonGroup.add(searchAll);
        searchAll.setForeground(new java.awt.Color(51, 102, 255));
        searchAll.setText("Search All");
        searchAll.setToolTipText("");
        searchAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout accountManagement_paneLayout = new javax.swing.GroupLayout(accountManagement_pane);
        accountManagement_pane.setLayout(accountManagement_paneLayout);
        accountManagement_paneLayout.setHorizontalGroup(
            accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountManagement_paneLayout.createSequentialGroup()
                .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accountManagement_paneLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteEmployee_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serachEmployee_lable1))
                        .addGap(18, 18, 18)
                        .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editEmployee_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectedEmployee_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(accountManagement_paneLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(serachEmployee_lable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serachBox_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchByEmpNo_radio)
                            .addComponent(searchByName_radio)
                            .addComponent(searchAll))
                        .addGap(18, 18, 18)
                        .addComponent(search_button, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        accountManagement_paneLayout.setVerticalGroup(
            accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountManagement_paneLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(serachEmployee_lable)
                        .addComponent(serachBox_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(search_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountManagement_paneLayout.createSequentialGroup()
                        .addComponent(searchAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchByName_radio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchByEmpNo_radio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serachEmployee_lable1)
                    .addComponent(selectedEmployee_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(accountManagement_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editEmployee_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteEmployee_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        masterLayered_pane.setLayer(service_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        masterLayered_pane.setLayer(accountMgmt_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        masterLayered_pane.setLayer(accountManagement_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout masterLayered_paneLayout = new javax.swing.GroupLayout(masterLayered_pane);
        masterLayered_pane.setLayout(masterLayered_paneLayout);
        masterLayered_paneLayout.setHorizontalGroup(
            masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addComponent(service_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 36, Short.MAX_VALUE)))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(accountMgmt_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(accountManagement_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(11, Short.MAX_VALUE)))
        );
        masterLayered_paneLayout.setVerticalGroup(
            masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 659, Short.MAX_VALUE)
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(service_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(accountMgmt_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(19, Short.MAX_VALUE)))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(accountManagement_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        closeButton1.setBackground(new java.awt.Color(0, 102, 255));
        closeButton1.setForeground(new java.awt.Color(255, 255, 255));
        closeButton1.setText("<");
        closeButton1.setToolTipText("");
        closeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton1ActionPerformed(evt);
            }
        });

        closeButton2.setBackground(new java.awt.Color(0, 102, 255));
        closeButton2.setForeground(new java.awt.Color(255, 255, 255));
        closeButton2.setText("=");
        closeButton2.setToolTipText("");
        closeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton2ActionPerformed(evt);
            }
        });

        closeButton3.setBackground(new java.awt.Color(0, 102, 255));
        closeButton3.setForeground(new java.awt.Color(255, 255, 255));
        closeButton3.setText("-");
        closeButton3.setToolTipText("");
        closeButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton3ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout master_paneLayout = new javax.swing.GroupLayout(master_pane);
        master_pane.setLayout(master_paneLayout);
        master_paneLayout.setHorizontalGroup(
            master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(master_paneLayout.createSequentialGroup()
                .addComponent(style1_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(master_paneLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(masterLayered_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        master_paneLayout.setVerticalGroup(
            master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(style1_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(master_paneLayout.createSequentialGroup()
                .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(master_paneLayout.createSequentialGroup()
                        .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(masterLayered_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(master_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(master_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void servicing_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicing_buttonActionPerformed
        // TODO add your handling code here:
        
        service_pane.setVisible(true);
        accountMgmt_pane.setVisible(false);
        accountManagement_pane.setVisible(false);
         employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
         searchAll.doClick();
    }//GEN-LAST:event_servicing_buttonActionPerformed

    private void employeeSignUp_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeSignUp_buttonActionPerformed
        // TODO add your handling code here:
        service_pane.setVisible(false);
        accountMgmt_pane.setVisible(false);
        accountManagement_pane.setVisible(true);
         employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
         searchAll.doClick();
    }//GEN-LAST:event_employeeSignUp_buttonActionPerformed

    private void accountMgmt_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountMgmt_buttonActionPerformed
        // TODO add your handling code here:
        
        service_pane.setVisible(false);
        accountMgmt_pane.setVisible(true);
        accountManagement_pane.setVisible(false);
        searchAll.doClick();

    }//GEN-LAST:event_accountMgmt_buttonActionPerformed

    private void closeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton1ActionPerformed
        // TODO add your handling code here:
         
        new mainMenu(username, cn).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_closeButton1ActionPerformed

    private void lastName_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastName_textboxMouseClicked
        // TODO add your handling code here:
        error2_lable.setVisible(false);
    }//GEN-LAST:event_lastName_textboxMouseClicked

    private void lastName_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastName_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastName_textboxActionPerformed

    private void accountMake_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountMake_buttonActionPerformed
        //initialization of variables
        String first_name = "";
        String last_name = "";
        String username = "";
        String password = "";
        String authority_level = "";
        String gender = "";
        String empNo = "";
        String dob = "";
        String passwordRe = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //entering the data into the variables
        first_name = firstName_textbox.getText();
        last_name = lastName_textbox.getText();
        username = username_textbox.getText();
        password = password_textbox.getText();
        authority_level = String.valueOf(authorityLevel_combobox.getSelectedItem());
        gender = String.valueOf(gender_combobox.getSelectedItem());
        empNo = employeeNo_textbox.getText();
        //get the date from the pane
        try{
        dob = sdf.format(dob_datechooser.getDate());}
        catch(NullPointerException e){System.out.println(e);}
        passwordRe = passwordRe_textbox.getText();
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        String Today = sdf.format(today);
        String check = "";
        //checks 
        boolean c0 = (dob.compareTo(Today)>0);
        boolean c01 = dob.equals("");
        boolean c1 = first_name.equals(check);
        boolean c2 = last_name.equals(check);
        boolean c3 = username.equals(check);
        boolean c4 = password.equals(check);
        boolean c5 = empNo.equals(check);
        boolean c6 = password.equals(passwordRe);
        //checking the checks created earlier
        if (first_name.equals(check)){error1_lable.setVisible(true);}
        if (last_name.equals(check)){error2_lable.setVisible(true);}
        if (username.equals(check)){error4_lable.setVisible(true);}
        if (password.equals(check)){error5_lable.setVisible(true);}
        if (empNo.equals(check)){error7_lable.setVisible(true);}
        if (c0){error3_lable.setVisible(true);}
        if (c01){error3_lable.setVisible(true);}
        if (c6 == false){error6_lable.setVisible(true);}
        //execution of the process
        if (c1==false && c2==false && c3==false && c4==false && c5==false && c0 == false && c6 == true )
        {error_disable(); dob_datechooser.setDate(null);
        new Security_questions(first_name, last_name, username, password, authority_level,gender,empNo ,dob,passwordRe, cn).setVisible(true);
        }
    }//GEN-LAST:event_accountMake_buttonActionPerformed

    private void dob_datechooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dob_datechooserMouseClicked
        // TODO add your handling code here:
        error3_lable.setVisible(false);
    }//GEN-LAST:event_dob_datechooserMouseClicked

    private void dob_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dob_labelMouseClicked
        // TODO add your handling code here:
        error4_lable.setVisible(false);
    }//GEN-LAST:event_dob_labelMouseClicked

    private void gender_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gender_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_gender_labelMouseClicked

    private void firstName_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstName_textboxMouseClicked
        // TODO add your handling code here:
        error1_lable.setVisible(false);
    }//GEN-LAST:event_firstName_textboxMouseClicked

    private void firstName_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstName_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstName_textboxActionPerformed

    private void employeeNo_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeNo_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeNo_labelMouseClicked

    private void employeeNo_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeNo_textboxMouseClicked
        // TODO add your handling code here:
        error7_lable.setVisible(false);
    }//GEN-LAST:event_employeeNo_textboxMouseClicked

    private void password_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_password_textboxMouseClicked
        // TODO add your handling code here:
        error5_lable.setVisible(false);
    }//GEN-LAST:event_password_textboxMouseClicked

    private void password_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_password_textboxActionPerformed

    private void username_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_username_textboxMouseClicked
        // TODO add your handling code here:
        error4_lable.setVisible(false);
    }//GEN-LAST:event_username_textboxMouseClicked

    private void username_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_username_textboxActionPerformed

    private void passwordRe_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordRe_textboxMouseClicked
        // TODO add your handling code here:
        error6_lable.setVisible(false);
    }//GEN-LAST:event_passwordRe_textboxMouseClicked

    private void passwordRe_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordRe_textboxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_passwordRe_textboxActionPerformed

    private void putRoomInOutOfService_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_putRoomInOutOfService_buttonActionPerformed
        // TODO add your handling code here:
        
        new putRoom_inOutService( cn).setVisible(true);
    }//GEN-LAST:event_putRoomInOutOfService_buttonActionPerformed

    private void addRemoveRoom_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRemoveRoom_buttonActionPerformed
        // TODO add your handling code here:
        
        new roomAdditionRemoval( cn).setVisible(true);
    }//GEN-LAST:event_addRemoveRoom_buttonActionPerformed

    private void addRemoveRoomType_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRemoveRoomType_buttonActionPerformed
        // TODO add your handling code here:
        new add_remove_roomTypes(cn).setVisible(true);
    }//GEN-LAST:event_addRemoveRoomType_buttonActionPerformed

    private void searchByName_radioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByName_radioActionPerformed
        // TODO add your handling code here:
        SearchBy = "searchBylastName";
    }//GEN-LAST:event_searchByName_radioActionPerformed

    private void searchByEmpNo_radioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByEmpNo_radioActionPerformed
        // TODO add your handling code here:
        SearchBy = "searchByEmployeeNo";
    }//GEN-LAST:event_searchByEmpNo_radioActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        // TODO add your handling code here:
        if(SearchBy.equals("searchBylastName"))
        {
            String searchby = serachBox_textfield.getText();
            employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
            DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
            try{
                String sql = ("SELECT * FROM `login_employee` WHERE `Last_name` = '"+searchby+"'");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                    String EmployeeNo = rs.getString("Emp.no");
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    String gender = rs.getString("Gender");
                    String dateOfBirth = rs.getString("Date_of_birth");
                    String authority = rs.getString("authority");
                    model.addRow(new Object [] {EmployeeNo,firstName,lastName,gender,dateOfBirth,authority});

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }
        if(SearchBy.equals("searchByEmployeeNo"))
        {
            String searchby = serachBox_textfield.getText();
            employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
            DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
            try{
                String sql = ("SELECT * FROM `login_employee` WHERE `Emp.no` = '"+searchby+"'");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                    String EmployeeNo = rs.getString("Emp.no");
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    String gender = rs.getString("Gender");
                    String dateOfBirth = rs.getString("Date_of_birth");
                    String authority = rs.getString("authority");
                    model.addRow(new Object [] {EmployeeNo,firstName,lastName,gender,dateOfBirth,authority});

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }
        if(SearchBy.equals(""))
        {

            employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
            DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
            try{
                String sql = ("SELECT * FROM `login_employee` ");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                    String EmployeeNo = rs.getString("Emp.no");
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    String gender = rs.getString("Gender");
                    String dateOfBirth = rs.getString("Date_of_birth");
                    String authority = rs.getString("authority");
                    model.addRow(new Object [] {EmployeeNo,firstName,lastName,gender,dateOfBirth,authority});

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }
    }//GEN-LAST:event_search_buttonActionPerformed

    private void employeeTable_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTable_tableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
        int select = employeeTable_table.getSelectedRow();
        String selectedEmp =model.getValueAt(select,0).toString();
        selectedEmployee_textfield.setText(selectedEmp);
        String Firstname = model.getValueAt(select, 1).toString();
        String Lastname = model.getValueAt(select,2).toString();;
        String fullname = Firstname+" "+Lastname;
        System.out.println(fullname);
        System.out.println(selectedEmp);
    }//GEN-LAST:event_employeeTable_tableMouseClicked

    private void deleteEmployee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmployee_buttonActionPerformed
        // TODO add your handling code here:efwg
        DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
        int select = employeeTable_table.getSelectedRow();
        String selectedEmp =model.getValueAt(select,0).toString();
        String Firstname = model.getValueAt(select, 1).toString();
        String Lastname = model.getValueAt(select,2).toString();;
        String fullname = Firstname+" "+Lastname;
        System.out.println(fullname);

        new employeeDelete_confirmation(selectedEmp,fullname, cn).setVisible(true);
        employeeTable_table.setModel(new DefaultTableModel(null, new String[] {"Employee No.","First Name","Last Name","Gender","Date Of Birth","Authority"}));
    }//GEN-LAST:event_deleteEmployee_buttonActionPerformed

    private void selectedEmployee_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedEmployee_textfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectedEmployee_textfieldActionPerformed

    private void editEmployee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEmployee_buttonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)employeeTable_table.getModel();
        int select = employeeTable_table.getSelectedRow();
        String selectedEmp =model.getValueAt(select,0).toString();
        System.out.println(selectedEmp);
        try {
            new employee_edit(selectedEmp, cn).setVisible(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_editEmployee_buttonActionPerformed

    private void searchAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAllActionPerformed
        // TODO add your handling code here:
        
        SearchBy = "";
    }//GEN-LAST:event_searchAllActionPerformed

    private void employeeSignUp_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeSignUp_button1ActionPerformed
        // TODO add your handling code here:
       new print_records(username, cn).setVisible(true);
       this.dispose();
    }//GEN-LAST:event_employeeSignUp_button1ActionPerformed

    private void closeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton2ActionPerformed
        // TODO add your handling code here:

        // xtxfhxcrxytjcjrxjxcty
        if(size)
        {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            int x =this.getHeight();
            int e = this.getWidth();
            System.out.println(e+" "+x);
            size = false;
        }
        else
        {
            this.setSize(997, 678);
            this.setLocationRelativeTo(null);
            size = true;
        }

    }//GEN-LAST:event_closeButton2ActionPerformed

    private void closeButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton3ActionPerformed
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_closeButton3ActionPerformed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-yy);

    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed

        xx = evt.getX();
        yy = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel1MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(admin_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin_options().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountMake_button;
    private javax.swing.JPanel accountManagement_pane;
    private javax.swing.JButton accountMgmt_button;
    private javax.swing.JPanel accountMgmt_pane;
    private javax.swing.JButton addRemoveRoomType_button;
    private javax.swing.JButton addRemoveRoom_button;
    private javax.swing.JLabel addRoom_lable;
    private javax.swing.JLabel addRoom_lable1;
    private javax.swing.JComboBox<String> authorityLevel_combobox;
    private javax.swing.JLabel authorityLevel_label;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton closeButton1;
    private javax.swing.JButton closeButton2;
    private javax.swing.JButton closeButton3;
    private javax.swing.JButton deleteEmployee_button;
    private com.toedter.calendar.JDateChooser dob_datechooser;
    private javax.swing.JLabel dob_label;
    private javax.swing.JButton editEmployee_button;
    private javax.swing.JLabel employeeNo_label;
    private javax.swing.JTextField employeeNo_textbox;
    private javax.swing.JButton employeeSignUp_button;
    private javax.swing.JButton employeeSignUp_button1;
    private javax.swing.JTable employeeTable_table;
    private javax.swing.JLabel error1_lable;
    private javax.swing.JLabel error2_lable;
    private javax.swing.JLabel error3_lable;
    private javax.swing.JLabel error4_lable;
    private javax.swing.JLabel error5_lable;
    private javax.swing.JLabel error6_lable;
    private javax.swing.JLabel error7_lable;
    private javax.swing.JLabel firstName_lable1;
    private javax.swing.JTextField firstName_textbox;
    private javax.swing.JComboBox<String> gender_combobox;
    private javax.swing.JLabel gender_label;
    private javax.swing.JLabel hotel_lable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastName_lable1;
    private javax.swing.JTextField lastName_textbox;
    private javax.swing.JLayeredPane masterLayered_pane;
    private javax.swing.JPanel master_pane;
    private javax.swing.JLabel passwordRe_lable;
    private javax.swing.JTextField passwordRe_textbox;
    private javax.swing.JLabel password_lable;
    private javax.swing.JTextField password_textbox;
    private javax.swing.JButton putRoomInOutOfService_button;
    private javax.swing.JLabel removeRoomOutOfService_lable;
    private javax.swing.JLabel reservation_lable;
    private javax.swing.JRadioButton searchAll;
    private javax.swing.JRadioButton searchByEmpNo_radio;
    private javax.swing.JRadioButton searchByName_radio;
    private javax.swing.ButtonGroup searchBy_buttonGroup;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField selectedEmployee_textfield;
    private javax.swing.JTextField serachBox_textfield;
    private javax.swing.JLabel serachEmployee_lable;
    private javax.swing.JLabel serachEmployee_lable1;
    private javax.swing.JPanel service_pane;
    private javax.swing.JButton servicing_button;
    private javax.swing.JPanel style1_pane;
    private javax.swing.JLabel username_lable;
    private javax.swing.JTextField username_textbox;
    // End of variables declaration//GEN-END:variables
}
